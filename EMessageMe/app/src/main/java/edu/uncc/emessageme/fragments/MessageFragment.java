package edu.uncc.emessageme.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.uncc.emessageme.R;
import edu.uncc.emessageme.databinding.FragmentMessageBinding;
import edu.uncc.emessageme.databinding.FragmentMessagesBinding;
import edu.uncc.emessageme.models.Message;

public class MessageFragment extends Fragment {
    private static final String ARG_PARAM_MESSAGE = "ARG_PARAM_MESSAGE";
    private Message mMessage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FragmentMessageBinding binding;

    public MessageFragment() {
        // Required empty public constructor
    }
    public static MessageFragment newInstance(Message message){
        MessageFragment fragment = new MessageFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mMessage.getReceiverId().equals(mAuth.getUid())){
            binding.textViewFromTo.setText("From");
            binding.textViewUser.setText(mMessage.getSenderName());
            binding.textView5.setVisibility(View.VISIBLE);
            binding.textViewReply.setVisibility(View.VISIBLE);
            setReceiverRead();
        }else{
            binding.textViewFromTo.setText("To");
            binding.textViewUser.setText(mMessage.getReceiverName());
            binding.textView5.setVisibility(View.INVISIBLE);
            binding.textViewReply.setVisibility(View.INVISIBLE);
        }

        if(mMessage.getDate() == null) {
            binding.textViewDate.setText("N/A");
        } else {
            Date date = mMessage.getDate().toDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            binding.textViewDate.setText(sdf.format(date));
        }
        binding.textViewTitle.setText(mMessage.getTitle());
        binding.textViewMessageText.setText(mMessage.getMessageText());

        binding.textViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoReply(mMessage);
            }
        });

        binding.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

    }

    void setReceiverRead() {
        DocumentReference docRef = db.collection("Messages").document(mMessage.getMessageId());
        HashMap<String, Object> data = new HashMap<>();
        data.put("receiverOpen", true);
        docRef.update(data);
    }

    MessageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (MessageListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MessageListener");
        }
    }

    public interface MessageListener{
        void cancel();
        void gotoReply(Message message);
    }
}