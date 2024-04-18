package com.example.room.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.room.AppDatabase;
import com.example.room.UserLog;
import com.example.room.databinding.FragmentMainBinding;
import com.example.room.databinding.LogRowItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends Fragment {

    ArrayList<UserLog> userLogs = new ArrayList<>();
    AppDatabase db;
    FragmentMainBinding binding;
    LogAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LogAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddLog();
            }
        });

        binding.buttonVisualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoVisualize();
            }
        });

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "userlogs-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        loadAndDisplayData();
    }

    void loadAndDisplayData(){
        userLogs.clear();
        userLogs.addAll(db.userLogDao().getAllOrderByDate());
        adapter.notifyDataSetChanged();
    }

    class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder>{
        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LogRowItemBinding rowBinding = LogRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new LogViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            UserLog userLog = userLogs.get(position);
            holder.setupUI(userLog);
        }

        @Override
        public int getItemCount() {
            return userLogs.size();
        }

        class LogViewHolder extends RecyclerView.ViewHolder{
            LogRowItemBinding mBinding;
            UserLog userLog;
            public LogViewHolder(@NonNull LogRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }
            public void setupUI(UserLog userLog){
                this.userLog = userLog;

                Date date = new Date(userLog.getDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String formattedDate = sdf.format(date);

                mBinding.textViewTime.setText(formattedDate);
                mBinding.textViewSleepHours.setText(userLog.getSleepHours() + " Hours");

                int sleepQuality = userLog.getSleepQuality();
                String strSleepQuality;
                if(sleepQuality == 1){
                    strSleepQuality = "Poor";
                }else if(sleepQuality == 2){
                    strSleepQuality = "Fair";
                }else if(sleepQuality == 3){
                    strSleepQuality = "Good";
                }else if(sleepQuality == 4){
                    strSleepQuality = "Very good";
                }else{
                    strSleepQuality = "Excellent";
                }

                mBinding.textViewSleepQuality.setText(strSleepQuality);
                mBinding.textViewExerciseHours.setText(userLog.getExerciseHours() + " Hours");
                mBinding.textViewWeight.setText(userLog.getWeight() + " lbs");

                mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.userLogDao().delete(userLog);
                        loadAndDisplayData();
                    }
                });
            }
        }
    }

    LogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LogListener) {
            mListener = (LogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LogListener");
        }
    }

    public interface LogListener {
        void gotoAddLog();
        void gotoVisualize();
    }
}