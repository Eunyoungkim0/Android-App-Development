package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectIncomeBinding;
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;

public class SelectIncomeFragment extends Fragment {

    public SelectIncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectIncomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectIncomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Income Fragment");

        binding.seekBar.setProgress(2);
        binding.textViewHouseHoldIncome.setText("$50K to <$100K");

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String str_progress = "";

                if(progress == 0){
                    str_progress = "<$25K";
                }else if(progress == 1){
                    str_progress = "$25K to <$50K";
                }else if(progress == 2){
                    str_progress = "$50K to <$100K";
                }else if(progress == 3){
                    str_progress = "$100K to <$200K";
                }else if(progress == 4){
                    str_progress = ">$200K";
                }

                binding.textViewHouseHoldIncome.setText(str_progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeFragment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_income = binding.textViewHouseHoldIncome.getText().toString();
                mListener.submitIncome(str_income);
            }
        });
    }

    SelectIncomeFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectIncomeFragmentListener) context;
    }

    public interface SelectIncomeFragmentListener{
        void closeFragment();
        void submitIncome(String str_income);
    }
}