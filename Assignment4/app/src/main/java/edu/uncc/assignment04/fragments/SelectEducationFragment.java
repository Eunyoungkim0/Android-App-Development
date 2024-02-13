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
import edu.uncc.assignment04.databinding.FragmentSelectEducationBinding;

public class SelectEducationFragment extends Fragment {

    public SelectEducationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectEducationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectEducationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Education Fragment");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeFragment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int education = binding.radioGroup.getCheckedRadioButtonId();
                String str_education = "";

                if(education == R.id.radioButtonBHS){
                    str_education = "Below High School";
                }else if(education == R.id.radioButtonHS){
                    str_education = "High School";
                }else if(education == R.id.radioButtonBS){
                    str_education = "Bachelor's Degree";
                }else if(education == R.id.radioButtonMS){
                    str_education = "Master's Degree";
                }else if(education == R.id.radioButtonPHD){
                    str_education = "Ph.D. or higher";
                }else if(education == R.id.radioButtonTS){
                    str_education = "Trade School";
                }else if(education == R.id.radioButtonPreferNotToSay){
                    str_education = "Prefer not to say";
                }

                if(str_education.isEmpty()){
                    Toast.makeText(getActivity(), "Please select education", Toast.LENGTH_SHORT).show();
                }else{
                    mListener.submitEducation(str_education);
                }

            }
        });
    }

    SelectEducationFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectEducationFragmentListener) context;
    }

    public interface SelectEducationFragmentListener{
        void closeFragment();
        void submitEducation(String str_education);
    }
}