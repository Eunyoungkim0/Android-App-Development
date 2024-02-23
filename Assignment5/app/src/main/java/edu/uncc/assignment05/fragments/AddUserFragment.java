package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;

public class AddUserFragment extends Fragment {

    public AddUserFragment() {
        // Required empty public constructor
    }

    private String gender = null, state = null, group = null;
    private int age = 0;
    private User mUser;
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setGroup(String group){
        this.group = group;
    }
    public void setUser(User user){
        mUser = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Users Fragment");

        if(gender == null){
            binding.textViewGender.setText("N/A");
        } else {
            binding.textViewGender.setText(gender);
        }

        if(age == 0){
            binding.textViewAge.setText("N/A");
        } else {
            binding.textViewAge.setText(age + " years old");
        }

        if(state == null){
            binding.textViewState.setText("N/A");
        } else {
            binding.textViewState.setText(state);
        }

        if(group == null){
            binding.textViewGroup.setText("N/A");
        } else {
            binding.textViewGroup.setText(group);
        }

        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGender();
            }
        });

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectAge();
            }
        });

        binding.buttonSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectState();
            }
        });

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGroup();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_name = binding.editTextName.getText().toString();
                String usr_email = binding.editTextEmail.getText().toString();
                String usr_gender = binding.textViewGender.getText().toString();
                String usr_age = binding.textViewAge.getText().toString();
                String usr_state = binding.textViewState.getText().toString();
                String usr_group = binding.textViewGroup.getText().toString();

                if(usr_name.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                }else if(usr_email.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                }else if(usr_gender.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select gender", Toast.LENGTH_SHORT).show();
                }else if(usr_age.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select age", Toast.LENGTH_SHORT).show();
                }else if(usr_state.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
                }else if(usr_group.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select group", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User(usr_name, usr_email, usr_gender, usr_age, usr_state, usr_group);
                    mListener.submitAddingNewUser(user);
                }
            }
        });
    }

    AddUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddUserFragmentListener) context;
    }

    public interface AddUserFragmentListener{
        void gotoSelectGender();
        void gotoSelectAge();
        void gotoSelectState();
        void gotoSelectGroup();
        void submitAddingNewUser(User user);
    }
}