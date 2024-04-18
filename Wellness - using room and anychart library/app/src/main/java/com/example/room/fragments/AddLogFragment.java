package com.example.room.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.room.AppDatabase;
import com.example.room.UserLog;
import com.example.room.databinding.FragmentAddLogBinding;
import com.example.room.model.SleepQuality;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLogFragment extends Fragment {
    FragmentAddLogBinding binding;
    public String selectedDate;
    public String selectedTime;
    public String time;
    public double selectedSleepHours = 0.0;
    public SleepQuality selectedSleepQuality = null;
    public double selectedExerciseHours = 0.0;

    public AddLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(selectedDate != null){
            binding.textViewDate.setText(selectedDate);
        }else{
            binding.textViewDate.setText("N/A");
        }
        if(selectedTime != null){
            binding.textViewTime.setText(selectedTime);
        }else{
            binding.textViewTime.setText("N/A");
        }
        if(selectedSleepHours > 0){
            binding.textViewSleepHours.setText(selectedSleepHours + " Hours");
        }else{
            binding.textViewSleepHours.setText("N/A");
        }
        if(selectedSleepQuality != null){
            binding.textViewSleepQuality.setText(selectedSleepQuality + "");
        }else{
            binding.textViewSleepQuality.setText("N/A");
        }
        if(selectedExerciseHours > 0){
            binding.textViewExerciseHours.setText(selectedExerciseHours + " Hours");
        }else{
            binding.textViewExerciseHours.setText("N/A");
        }

        binding.pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment().show(getChildFragmentManager(), "datePicker");
            }
        });
        binding.pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment().show(getChildFragmentManager(), "timePicker");
            }
        });

        binding.buttonSleepHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSleepHours();
            }
        });

        binding.buttonSleepQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSleepQuality();
            }
        });

        binding.buttonExerciseHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectExerciseHours();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightText = binding.editTextWeight.getText().toString();


                if(selectedDate == null || selectedDate.isEmpty()){
                    Toast.makeText(getActivity(), "Select date", Toast.LENGTH_SHORT).show();
                }else if(selectedTime == null || selectedTime.isEmpty()){
                    Toast.makeText(getActivity(), "Select time", Toast.LENGTH_SHORT).show();
                }else if(selectedSleepHours == 0.0){
                    Toast.makeText(getActivity(), "Select sleep hours", Toast.LENGTH_SHORT).show();
                }else if(selectedSleepQuality == null){
                    Toast.makeText(getActivity(), "Select sleep quality", Toast.LENGTH_SHORT).show();
                }else if(selectedExerciseHours == 0.0){
                    Toast.makeText(getActivity(), "Select exercise hours", Toast.LENGTH_SHORT).show();
                }else if(weightText.equals("")){
                    Toast.makeText(getActivity(), "Enter weight", Toast.LENGTH_SHORT).show();
                }else{
                    double usrWeight = Double.parseDouble(weightText);

                    addLog(selectedDate, time, selectedSleepHours, selectedSleepQuality, selectedExerciseHours, usrWeight);
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

    }

    void addLog(String date, String time, double sleepHours, SleepQuality sleepQuality, double exerciseHours, double weight){

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "userlogs-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        String dateTimeString = date + " " + time;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateTime;
        try {
            dateTime = sdf.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        long dateTimeMillis = dateTime.getTime();

        UserLog userLog = new UserLog(dateTimeMillis, sleepHours, sleepQuality.int_quality, exerciseHours, weight);
        db.userLogDao().insertAll(userLog);
        mListener.doneAddLog();
    }

    public void setTime(int hour, int minute){
        time = hour + ":" + String.format("%02d", minute);

        String ampm;
        if(hour > 12){
            hour = hour - 12;
            ampm = "PM";
        }else if(hour == 12){
            ampm = "PM";
        }else if(hour == 0){
            hour = 12;
            ampm = "AM";
        }else{
            ampm = "AM";
        }
        selectedTime = hour + ":" + String.format("%02d", minute) + " " + ampm;

        binding.textViewTime.setText(selectedTime);
    }

    public void setDate(int year, int month, int day){
        selectedDate = String.format("%04d", year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", day);
        binding.textViewDate.setText(selectedDate);
    }

    AddLogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddLogListener) {
            mListener = (AddLogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddLogListener");
        }
    }


    public interface AddLogListener {
        void gotoSelectSleepHours();
        void gotoSelectSleepQuality();
        void gotoSelectExerciseHours();
        void doneAddLog();
        void cancel();
    }
}