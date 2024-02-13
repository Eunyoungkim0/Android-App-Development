package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectEducationBinding;
import edu.uncc.assignment04.databinding.FragmentSelectMaritalStatusBinding;

public class SelectMaritalStatusFragment extends Fragment {

    public SelectMaritalStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectMaritalStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Marital Status Fragment");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeFragment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maritalstatus = binding.radioGroup.getCheckedRadioButtonId();
                String str_marital_status = "";

                if(maritalstatus == R.id.radioButtonNotMarried){
                    str_marital_status = "Not Married";
                }else if(maritalstatus == R.id.radioButtonMarried){
                    str_marital_status = "Married";
                }else if(maritalstatus == R.id.radioButtonPreferNotToSay){
                    str_marital_status = "Prefer not to say";
                }

                if(str_marital_status.isEmpty()){
                    Toast.makeText(getActivity(), "Please select marital status", Toast.LENGTH_SHORT).show();
                }else{
                    mListener.submitMaritalStatus(str_marital_status);
                }

            }
        });
    }

    SelectMaritalStatusFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectMaritalStatusFragmentListener) context;
    }

    public interface SelectMaritalStatusFragmentListener{
        void closeFragment();
        void submitMaritalStatus(String str_marital_status);
    }
}