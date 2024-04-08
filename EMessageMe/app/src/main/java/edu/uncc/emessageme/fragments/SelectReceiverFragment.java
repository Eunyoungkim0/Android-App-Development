package edu.uncc.emessageme.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.emessageme.R;
import edu.uncc.emessageme.databinding.FragmentCreateMessageBinding;
import edu.uncc.emessageme.databinding.FragmentSelectReceiverBinding;
import edu.uncc.emessageme.databinding.ReceiverRowItemBinding;
import edu.uncc.emessageme.models.Message;
import edu.uncc.emessageme.models.User;

public class SelectReceiverFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FragmentSelectReceiverBinding binding;
    UsersAdapter usersAdapter;
    ArrayList<User> mUsers = new ArrayList<>();
    ArrayList<String> blockedBy = new ArrayList<>();
    ListenerRegistration listenerRegistration;

    public SelectReceiverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectReceiverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersAdapter = new UsersAdapter(getActivity(), R.layout.receiver_row_item, mUsers);
        binding.listView.setAdapter(usersAdapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = mUsers.get(position);
                Log.d("demo", "(SelectReceiverFragment) onItemClick: " + user);
                mListener.submitReceiver(user);
            }
        });

        binding.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        getUsersBlockedMe();
    }


    void getUsersBlockedMe(){
        db.collection("Users").whereEqualTo("userId", mAuth.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        blockedBy.clear();
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                ArrayList<String> tmpBlockedBy = (ArrayList<String>) documentSnapshot.get("blockedBy");
                                if(tmpBlockedBy != null){
                                    for(String a: tmpBlockedBy){
                                        blockedBy.add(a);
                                    }
                                }
                            }

                            getAllUsers();
                        }
                    }
                });
    }

    void getAllUsers(){
        blockedBy.add(mAuth.getUid());

        listenerRegistration = db.collection("Users")
                .whereNotIn("userId", blockedBy)
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


    class UsersAdapter extends ArrayAdapter<User> {
        public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ReceiverRowItemBinding itemBinding;

            if(convertView == null){
                itemBinding = ReceiverRowItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ReceiverRowItemBinding) convertView.getTag();
            }

            User receiver = getItem(position);
            itemBinding.textViewUserName.setText(receiver.getUserName());
            return convertView;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
            mUsers.clear();
            blockedBy.clear();
        }
    }

    SelectReceiverListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SelectReceiverListener){
            mListener = (SelectReceiverListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectReceiverListener");
        }
    }

    public interface SelectReceiverListener {
        void cancel();
        void submitReceiver(User user);
    }

}