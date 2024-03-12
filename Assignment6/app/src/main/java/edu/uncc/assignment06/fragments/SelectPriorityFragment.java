package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.databinding.SelectionListItemBinding;
import edu.uncc.assignment06.databinding.TaskListItemBinding;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class SelectPriorityFragment extends Fragment {
    LinearLayoutManager layoutManager;
    PriorityRecycleViewAdapter adapter;
    RecyclerView recyclerView;
    String[] priorities;

    public SelectPriorityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priorities = Data.priorities;

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PriorityRecycleViewAdapter(priorities);
        recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }

    SelectPriorityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityListener) context;
    }

    public interface SelectPriorityListener{
        void cancel();
        void submitPriority(String priority, int intPriority);
    }

    class PriorityRecycleViewAdapter extends RecyclerView.Adapter<PriorityRecycleViewAdapter.PriorityViewHolder>{
        String[] priorities;

        public PriorityRecycleViewAdapter(String[] priorities){
            this.priorities = priorities;
        }

        @NonNull
        @Override
        public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SelectionListItemBinding mBinding = SelectionListItemBinding.inflate(getLayoutInflater(), parent, false);
            PriorityViewHolder holder = new PriorityViewHolder(mBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
            String priority = priorities[position];
            if(priority.equals("Very High")){
                holder.intPriority = 5;
            }else if(priority.equals("High")){
                holder.intPriority = 4;
            }else if(priority.equals("Medium")){
                holder.intPriority = 3;
            }else if(priority.equals("Low")){
                holder.intPriority = 2;
            }else if(priority.equals("Very Low")){
                holder.intPriority = 1;
            }
            holder.priority = priority;

            holder.setupUI(priority);
        }

        @Override
        public int getItemCount() {
            if (priorities != null) {
                return priorities.length;
            } else {
                return 0;
            }
        }

        public class PriorityViewHolder extends RecyclerView.ViewHolder {
            SelectionListItemBinding mBinding;
            String priority;
            int intPriority;
            public PriorityViewHolder(SelectionListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.submitPriority(priority, intPriority);
                    }
                });
            }

            public void setupUI(String priority){
                mBinding.textView.setText(priority);
            }
        }
    }
}