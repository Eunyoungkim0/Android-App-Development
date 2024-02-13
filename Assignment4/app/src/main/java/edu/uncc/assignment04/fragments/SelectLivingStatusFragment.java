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
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;
import edu.uncc.assignment04.databinding.FragmentSelectMaritalStatusBinding;

public class SelectLivingStatusFragment extends Fragment {

    public SelectLivingStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectLivingStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectLivingStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Living Status Fragment");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeFragment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int livingstatus = binding.radioGroup.getCheckedRadioButtonId();
                String str_living_status = "";

                if(livingstatus == R.id.radioButtonHomeOwner){
                    str_living_status = "Home Owner";
                }else if(livingstatus == R.id.radioButtonRenter){
                    str_living_status = "Renter";
                }else if(livingstatus == R.id.radioButtonLessee){
                    str_living_status = "Lessee";
                }else if(livingstatus == R.id.radioButtonOther){
                    str_living_status = "Other";
                }else if(livingstatus == R.id.radioButtonPreferNotToSay){
                    str_living_status = "Prefer not to say";
                }

                if(str_living_status.isEmpty()){
                    Toast.makeText(getActivity(), "Please select living status", Toast.LENGTH_SHORT).show();
                }else{
                    mListener.submitLivingStatus(str_living_status);
                }

            }
        });
    }

    SelectLivingStatusFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectLivingStatusFragmentListener) context;
    }

    public interface SelectLivingStatusFragmentListener{
        void closeFragment();
        void submitLivingStatus(String str_living_status);
    }
}