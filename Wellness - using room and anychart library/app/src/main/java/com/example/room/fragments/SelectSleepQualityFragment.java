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

import com.example.room.databinding.FragmentSelectSleepQualityBinding;
import com.example.room.model.SleepQuality;

import java.util.ArrayList;

public class SelectSleepQualityFragment extends Fragment {
    FragmentSelectSleepQualityBinding binding;

    ArrayList<SleepQuality> qualities = new ArrayList<>();
    ArrayAdapter<SleepQuality> adapter;

    public SelectSleepQualityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qualities.add(new SleepQuality(5, "Excellent"));
        qualities.add(new SleepQuality(4, "Very good"));
        qualities.add(new SleepQuality(3, "Good"));
        qualities.add(new SleepQuality(2, "Fair"));
        qualities.add(new SleepQuality(1, "Poor"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectSleepQualityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, qualities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SleepQuality sleepQuality = qualities.get(position);
                mListener.onSleepQualitySelected(sleepQuality);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }

    SleepQualityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SleepQualityListener) {
            mListener = (SleepQualityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SleepQualityListener");
        }
    }
    public interface SleepQualityListener {
        void onSleepQualitySelected(SleepQuality sleepQuality);
        void cancel();
    }
}