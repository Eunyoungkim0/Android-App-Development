package com.example.room;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.room.fragments.AddLogFragment;
import com.example.room.fragments.ChartsFragment;
import com.example.room.fragments.DatePickerFragment;
import com.example.room.fragments.MainFragment;
import com.example.room.fragments.SelectExerciseHoursFragment;
import com.example.room.fragments.SelectSleepHoursFragment;
import com.example.room.fragments.SelectSleepQualityFragment;
import com.example.room.fragments.TimePickerFragment;
import com.example.room.model.SleepQuality;

public class MainActivity extends AppCompatActivity
        implements MainFragment.LogListener, AddLogFragment.AddLogListener,
                    SelectSleepHoursFragment.SleepHoursListener,
                    SelectExerciseHoursFragment.ExerciseHoursListener,
                    SelectSleepQualityFragment.SleepQualityListener,
                    ChartsFragment.ChartsListener,
                    TimePickerFragment.TimePickerListener,
                    DatePickerFragment.DatePickerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddLogFragment(), "add-log-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ChartsFragment(), "charts-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSleepHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSleepHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSleepQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSleepQualityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectExerciseHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectExerciseHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneAddLog() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSleepHoursSelected(double hours) {

        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepHours = hours;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSleepQualitySelected(SleepQuality quality) {

        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepQuality = quality;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onExerciseHoursSelected(double hours) {

        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedExerciseHours = hours;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onTimeSelected(int hour, int minute) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.setTime(hour, minute);
        }
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.setDate(year, month, day);
        }
    }
}