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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.emessageme.R;
import edu.uncc.emessageme.databinding.FragmentCreateMessageBinding;
import edu.uncc.emessageme.databinding.FragmentMessagesBinding;
import edu.uncc.emessageme.models.Message;
import edu.uncc.emessageme.models.User;

public class CreateMessageFragment extends Fragment {
    private static final String ARG_PARAM_MESSAGE = "ARG_PARAM_MESSAGE";
    private Message mMessage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FragmentCreateMessageBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public User getReceiver;

    public CreateMessageFragment() {
        // Required empty public constructor
    }

    public static CreateMessageFragment newInstance(Message message){
        CreateMessageFragment fragment = new CreateMessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessage = (Message) getArguments().getSerializable(ARG_PARAM_MESSAGE);
            User user = new User(mMessage.getSenderId(), mMessage.getSenderName(), null, null);
            getReceiver = user;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(getReceiver != null){
            binding.textViewReceiver.setText(getReceiver.getUserName());
        }else{
            binding.textViewReceiver.setText("N/A");
        }

        Log.d("demo", "onViewCreated: view is created! and get Receiver is " + getReceiver);

        if(mMessage != null){
            binding.textViewMessage.setText("Reply to the Message");
            binding.buttonSelectUser.setVisibility(View.INVISIBLE);
            binding.editTextTitle.setText("[Re] " + mMessage.getTitle());
            binding.editTextTitle.setTextColor(getResources().getColor(R.color.black));
            binding.editTextTitle.setEnabled(false);
        }else{
            binding.textViewMessage.setText("Create Message");
            binding.buttonSelectUser.setVisibility(View.VISIBLE);
            binding.editTextTitle.setEnabled(true);
        }


        binding.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        binding.buttonSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectReceiver();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = binding.textViewReceiver.getText().toString();
                String title = binding.editTextTitle.getText().toString();
                String message = binding.editTextMessage.getText().toString();

                if(receiver.isEmpty() || receiver.equals("N/A")){
                    Toast.makeText(getActivity(), "Select receiver", Toast.LENGTH_SHORT).show();
                }else if(title.isEmpty()){
                    Toast.makeText(getActivity(), "Enter title", Toast.LENGTH_SHORT).show();
                }else if(message.isEmpty()){
                    Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference docRef = db.collection("Messages").document();
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("messageId", docRef.getId());
                    data.put("senderId", mAuth.getUid());
                    data.put("senderName", mAuth.getCurrentUser().getDisplayName());
                    data.put("receiverId", getReceiver.getUserId());
                    data.put("receiverName", getReceiver.getUserName());
                    data.put("title", title);
                    data.put("messageText", message);
                    data.put("date", FieldValue.serverTimestamp());
                    data.put("receiverOpen", false);
                    data.put("senderDelete", false);
                    data.put("receiverDelete", false);

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mListener.doneSendingMessage();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    CreateMessageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (CreateMessageListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CreateMessageListener");
        }
    }

    public interface CreateMessageListener {
        void cancel();
        void doneSendingMessage();
        void gotoSelectReceiver();
    }
}