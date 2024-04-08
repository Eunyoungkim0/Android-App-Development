package edu.uncc.emessageme.fragments;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.Filterable;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import edu.uncc.emessageme.R;
import edu.uncc.emessageme.databinding.FragmentMessagesBinding;
import edu.uncc.emessageme.databinding.MessageRowItemBinding;
import edu.uncc.emessageme.models.Message;


public class MessagesFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FragmentMessagesBinding binding;
    MessagesAdapter messagesAdapter;
    ArrayList<Message> mMessages = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter = new MessagesAdapter();
        binding.recyclerView.setAdapter(messagesAdapter);
        getActivity().setTitle("My MessageBox");

        binding.textViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateMessage();
            }
        });

        binding.textViewUserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoViewUsers();
            }
        });

        binding.textlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getAllMessages(newText);
                return false;
            }
        });

        getAllMessages("");
    }

    void getAllMessages(String newText){
        listenerRegistration = db.collection("Messages")
                .orderBy("title")
                .startAt(newText)
                .endAt(newText + "\uf8ff")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            error.printStackTrace();
                            return;
                        }
                        mMessages.clear();

                        for(QueryDocumentSnapshot document: value){
                            Message message = document.toObject(Message.class);

                            if((message.getReceiverId().equals(mAuth.getUid()) && !message.getReceiverDelete()) || (message.getSenderId().equals(mAuth.getUid()) && !message.getSenderDelete())){
                                mMessages.add(message);
                            }
                        }

                        Collections.sort(mMessages, new Comparator<Message>() {
                            @Override
                            public int compare(Message message1, Message message2) {
                                if (message1.getDate() == null && message2.getDate() == null) {
                                    return 0;
                                } else if (message1.getDate() == null) {
                                    return 1;
                                } else if (message2.getDate() == null) {
                                    return -1;
                                } else {
                                    return message2.getDate().compareTo(message1.getDate());
                                }
                            }
                        });

                        messagesAdapter.notifyDataSetChanged();
                    }
                });
    }



    class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

        @NonNull
        @Override
        public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MessageRowItemBinding binding = MessageRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new MessagesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
            Message message = mMessages.get(position);
            holder.setupUI(message);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        class MessagesViewHolder extends RecyclerView.ViewHolder {
            MessageRowItemBinding mBinding;
            Message mMessage;
            public MessagesViewHolder(MessageRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Message message){
                mMessage = message;
                boolean amISender = false;
                boolean amIReceiver = false;

                if(mAuth.getUid().equals(mMessage.getSenderId())){
                    amISender = true;
                }else if(mAuth.getUid().equals(mMessage.getReceiverId())){
                    amIReceiver = true;
                }

                if(amISender){
                    mBinding.getRoot().setBackgroundColor(getResources().getColor(R.color.lightgray));
                    mBinding.textName.setTextColor(getResources().getColor(R.color.green));
                    mBinding.textFromTo.setTextColor(getResources().getColor(R.color.green));
                    mBinding.textName.setText(mMessage.getReceiverName());
                    mBinding.textFromTo.setText("TO  : ");
                }else{
                    mBinding.textName.setTextColor(getResources().getColor(R.color.blue));
                    mBinding.textFromTo.setTextColor(getResources().getColor(R.color.blue));
                    mBinding.textName.setText(mMessage.getSenderName());
                    mBinding.textFromTo.setText("FROM: ");
                }

                mBinding.textTitle.setText(mMessage.getTitle());
                mBinding.textMessage.setText(mMessage.getMessageText());

                if(this.mMessage.getDate() == null) {
                    mBinding.textDate.setText("N/A");
                } else {
                    Date date = this.mMessage.getDate().toDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    mBinding.textDate.setText(sdf.format(date));
                }

                if(mMessage.getReceiverOpen()){
                    mBinding.imageViewRead.setVisibility(View.VISIBLE);
                    mBinding.textTitle.setTypeface(null, Typeface.NORMAL);
                }else{
                    mBinding.imageViewRead.setVisibility(View.INVISIBLE);
                    mBinding.textTitle.setTypeface(null, Typeface.BOLD);
                }

                boolean finalAmIReceiver = amIReceiver;
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference docRef = db.collection("Messages").document(mMessage.getMessageId());
                        HashMap<String, Object> data = new HashMap<>();

                        if(finalAmIReceiver) {
                            data.put("receiverDelete", true);
                        }else{
                            data.put("senderDelete", true);
                        }
                        docRef.update(data);
                    }
                });

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoMessageDetail(mMessage);
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
            mMessages.clear();
        }
    }

    MessagesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MessagesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MessagesListener");
        }
    }

    public interface MessagesListener {
        void gotoCreateMessage();
        void gotoMessageDetail(Message message);
        void gotoViewUsers();
        void logout();
    }
}