package edu.uncc.emessageme.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.emessageme.R;
import edu.uncc.emessageme.databinding.FragmentSelectReceiverBinding;
import edu.uncc.emessageme.databinding.FragmentUserListBinding;
import edu.uncc.emessageme.databinding.UserRowItemBinding;
import edu.uncc.emessageme.models.User;

public class UserListFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FragmentUserListBinding binding;
    UsersAdapter usersAdapter;
    ArrayList<User> mUsers = new ArrayList<>();
    ArrayList<String> blocked = new ArrayList<>();
    ListenerRegistration listenerRegistration;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersAdapter = new UsersAdapter();
        binding.recyclerView.setAdapter(usersAdapter);
        getActivity().setTitle("User List");

        binding.textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        binding.textViewBlocklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoBlockList();
            }
        });

        getUsersIBlocked();
    }

    void getUsersIBlocked(){
        db.collection("Users").whereEqualTo("userId", mAuth.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        blocked.clear();
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                ArrayList<String> tmpBlocked = (ArrayList<String>) documentSnapshot.get("blocked");
                                if(tmpBlocked != null){
                                    for(String a: tmpBlocked){
                                        blocked.add(a);
                                    }
                                }
                            }
                            getAllUsers();
                        }
                    }
                });
    }

    void getAllUsers() {
        blocked.add(mAuth.getUid());

        listenerRegistration = db.collection("Users")
                .whereNotIn("userId", blocked)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            error.printStackTrace();
                            return;
                        }

                        mUsers.clear();

                        for(QueryDocumentSnapshot document: value){
                            User user = document.toObject(User.class);
                            mUsers.add(user);
                        }
                        usersAdapter.notifyDataSetChanged();
                    }
                });
    }

    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UserRowItemBinding binding = UserRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new UsersViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
            User user = mUsers.get(position);
            holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class UsersViewHolder extends RecyclerView.ViewHolder {
            UserRowItemBinding mBinding;
            User mUser;
            public UsersViewHolder(UserRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(User user){
                mUser = user;

                mBinding.textName.setText(mUser.getUserName());

                mBinding.buttonBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blocked.add(mUser.getUserId());

                        DocumentReference docRef = db.collection("Users").document(mAuth.getUid());
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("blocked", FieldValue.arrayUnion(mUser.getUserId()));
                        docRef.update(data);

                        DocumentReference docRef2 = db.collection("Users").document(mUser.getUserId());
                        HashMap<String, Object> data2 = new HashMap<>();
                        data2.put("blockedBy", FieldValue.arrayUnion(mAuth.getUid()));
                        docRef2.update(data2);

                        getAllUsers();

                        Toast.makeText(getActivity(), "Successfully blocked the user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
            mUsers.clear();
            blocked.clear();
        }
    }

    UserListListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UserListListener){
            mListener = (UserListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UserListListener");
        }
    }
    public interface UserListListener {
        void cancel();
        void gotoBlockList();
    }
}