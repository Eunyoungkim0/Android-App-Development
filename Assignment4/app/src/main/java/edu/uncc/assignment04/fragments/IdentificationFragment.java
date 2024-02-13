package edu.uncc.assignment04.fragments;

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

import edu.uncc.assignment04.Identification;
import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentIdentificationBinding;
import edu.uncc.assignment04.databinding.FragmentMainBinding;

public class IdentificationFragment extends Fragment {

    public IdentificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Identification mIdentification;
    public void setIdentification(Identification identification){
        mIdentification = identification;
    }

    FragmentIdentificationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIdentificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Identification Fragment");

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                int role = binding.radioGroup.getCheckedRadioButtonId();
                String str_role = "";

                if(role == R.id.radioButtonStudent){
                    str_role = "Student";
                }else if(role == R.id.radioButtonEmployee){
                    str_role = "Employee";
                }else if(role == R.id.radioButtonOther){
                    str_role = "Other";
                }

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                }else if(str_role.isEmpty()){
                    Toast.makeText(getActivity(), "Please select role", Toast.LENGTH_SHORT).show();
                }else{
                    Identification identification = new Identification(name, email, str_role);
                    mListener.gotoDemographic(identification);
                }
            }
        });
    }

    IdentificationFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IdentificationFragmentListener) context;
    }

    public interface IdentificationFragmentListener{
        void gotoDemographic(Identification identification);
    }
}