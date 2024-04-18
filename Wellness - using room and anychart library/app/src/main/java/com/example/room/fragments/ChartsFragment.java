package com.example.room.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.room.AppDatabase;
import com.example.room.UserLog;
import com.example.room.databinding.ChartRowItemBinding;
import com.example.room.databinding.FragmentChartsBinding;

import com.anychart.data.Set;
import com.example.room.model.Chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartsFragment extends Fragment {
    ArrayList<UserLog> userLogs = new ArrayList<>();
    ArrayList<Chart> charts = new ArrayList<>();
    List<DataEntry> sleepHours = new ArrayList<>();
    List<DataEntry> sleepQualities = new ArrayList<>();
    List<DataEntry> exerciseHours = new ArrayList<>();
    List<DataEntry> weight = new ArrayList<>();
    AppDatabase db;
    FragmentChartsBinding binding;
    ChartsAdapter adapter;

    public ChartsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ChartsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        loadAndDisplayData();
    }

    void loadAndDisplayData(){

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "userlogs-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        userLogs.clear();
        userLogs.addAll(db.userLogDao().getAll());
        addDataForCharts();
        adapter.notifyDataSetChanged();
    }

    void addDataForCharts() {
        charts.clear();
        sleepHours.clear();
        sleepQualities.clear();
        exerciseHours.clear();
        weight.clear();
        for(int i=0; i<userLogs.size(); i++){
            UserLog userLog = userLogs.get(i);
            Date date = new Date(userLog.getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = sdf.format(date);
            sleepHours.add(new ValueDataEntry(formattedDate, userLog.getSleepHours()));
            sleepQualities.add(new ValueDataEntry(formattedDate, userLog.getSleepQuality()));
            exerciseHours.add(new ValueDataEntry(formattedDate, userLog.getExerciseHours()));
            weight.add(new ValueDataEntry(formattedDate, userLog.getWeight()));
        }
        charts.add(new Chart("Sleep Hours", sleepHours, "#FF5768"));
        charts.add(new Chart("Sleep Quality", sleepQualities, "#FFBF65"));
        charts.add(new Chart("Exercise Hours", exerciseHours, "#00CDAC"));
        charts.add(new Chart("Weight", weight, "#00A5E3"));
    }


    class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartsViewHolder>{
        @NonNull
        @Override
        public ChartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ChartRowItemBinding rowBinding = ChartRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ChartsViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ChartsViewHolder holder, int position) {
            Chart chart = charts.get(position);
            holder.setupUI(chart);
        }

        @Override
        public int getItemCount() {
            return charts.size();
        }

        class ChartsViewHolder extends RecyclerView.ViewHolder{
            ChartRowItemBinding mBinding;
            Chart chart;
            public ChartsViewHolder(@NonNull ChartRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }
            public void setupUI(Chart chart){
                this.chart = chart;

                mBinding.anyChartView.setProgressBar(mBinding.progressBar);
                Cartesian cartesian = AnyChart.line();

                cartesian.animation(true);

                cartesian.padding(10d, 20d, 5d, 20d);

                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                cartesian.title(chart.getTitle() + " Over Time");

                cartesian.yAxis(0).title("Number of " + chart.getTitle());
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                Set set = Set.instantiate();
                set.data(chart.getSeriesData());
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                Line series1 = cartesian.line(series1Mapping);
                series1.name(chart.getTitle());
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                series1.color(chart.getColor());

                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                mBinding.anyChartView.setChart(cartesian);
            }
        }
    }

    ChartsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ChartsListener) {
            mListener = (ChartsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ChartsListener");
        }
    }

    public interface ChartsListener {
        void cancel();
    }
}