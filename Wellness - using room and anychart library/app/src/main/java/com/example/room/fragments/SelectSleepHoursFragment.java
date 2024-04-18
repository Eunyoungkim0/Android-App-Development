package com.example.room.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.room.databinding.FragmentSelectSleepHoursBinding;

import java.util.ArrayList;

public class SelectSleepHoursFragment extends Fragment {
    FragmentSelectSleepHoursBinding binding;

    ArrayList<Double> hourlists = new ArrayList<>();
    ArrayAdapter<Double> adapter;

    public SelectSleepHoursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (double i = 0; i < 15 ; i+=0.5) {
            hourlists.add(i+0.5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectSleepHoursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, hourlists);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Double hour = hourlists.get(position);
                mListener.onSleepHoursSelected(hour);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }

    SleepHoursListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SleepHoursListener) {
            mListener = (SleepHoursListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SleepHoursListener");
        }
    }
    public interface SleepHoursListener {
        void onSleepHoursSelected(double hours);
        void cancel();
    }
}