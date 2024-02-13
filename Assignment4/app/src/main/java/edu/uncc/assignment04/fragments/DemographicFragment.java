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
import edu.uncc.assignment04.Profile;
import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;
import edu.uncc.assignment04.databinding.FragmentMainBinding;

public class DemographicFragment extends Fragment {
    private static final String ARG_IDENTIFICATION = "ARG_IDENTIFICATION";
    private Identification mIdentification;
    private String education = null;
    private String maritalstatus = null;
    private String livingstatus = null;
    private String income = null;
    public void setSelectEducation(String str_education){
        this.education = str_education;
    }

    public void setSelectMaritalStatus(String str_marital_status){
        this.maritalstatus = str_marital_status;
    }

    public void setSelectLivingStatus(String str_living_status){
        this.livingstatus = str_living_status;
    }

    public void setSelectIncome(String str_income){
        this.income = str_income;
    }

    public DemographicFragment() {
        // Required empty public constructor
    }

    public static DemographicFragment newInstance(Identification identification) {
        DemographicFragment fragment = new DemographicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_IDENTIFICATION, identification);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdentification = (Identification) getArguments().getSerializable(ARG_IDENTIFICATION);
        }
    }

    private Profile mProfile;
    public void setProfile(Profile profile){
        mProfile = profile;
    }

    FragmentDemographicBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDemographicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Demographic Fragment");

        binding.buttonSelectEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectEducation();
            }
        });

        binding.buttonSelectMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectMaritalStatus();
            }
        });

        binding.buttonSelectLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectLivingStatus();
            }
        });

        binding.buttonSelectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectIncome();
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.textViewEducation.getText().equals("N/A")){
                    Toast.makeText(getActivity(), "Please select education", Toast.LENGTH_SHORT).show();
                }else if(binding.textViewMaritalStatus.getText().equals("N/A")){
                    Toast.makeText(getActivity(), "Please select marital status", Toast.LENGTH_SHORT).show();
                }else if(binding.textViewLivingStatus.getText().equals("N/A")){
                    Toast.makeText(getActivity(), "Please select living status", Toast.LENGTH_SHORT).show();
                }else if(binding.textViewIncomeStatus.getText().equals("N/A")){
                    Toast.makeText(getActivity(), "Please select income status", Toast.LENGTH_SHORT).show();
                }else{
                    String name = mIdentification.getName();
                    String email = mIdentification.getEmail();
                    String str_role = mIdentification.getRole();
                    String education = binding.textViewEducation.getText().toString();
                    String maritalStatus = binding.textViewMaritalStatus.getText().toString();
                    String livingStatus = binding.textViewLivingStatus.getText().toString();
                    String income = binding.textViewIncomeStatus.getText().toString();
                    Profile profile = new Profile(name, email, str_role, education, maritalStatus, livingStatus, income);
                    mListener.gotoProfile(profile);
                }
            }
        });

        if(education == null){
            binding.textViewEducation.setText("N/A");
        } else {
            binding.textViewEducation.setText(education);
        }

        if(maritalstatus == null){
            binding.textViewMaritalStatus.setText("N/A");
        } else {
            binding.textViewMaritalStatus.setText(maritalstatus);
        }

        if(livingstatus == null){
            binding.textViewLivingStatus.setText("N/A");
        } else {
            binding.textViewLivingStatus.setText(livingstatus);
        }

        if(income == null){
            binding.textViewIncomeStatus.setText("N/A");
        } else {
            binding.textViewIncomeStatus.setText(income);
        }
    }

    DemographicFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DemographicFragmentListener) context;
    }
    public interface DemographicFragmentListener{
        void gotoSelectEducation();
        void gotoSelectMaritalStatus();
        void gotoSelectLivingStatus();
        void gotoSelectIncome();
        void gotoProfile(Profile profile);
    }
}