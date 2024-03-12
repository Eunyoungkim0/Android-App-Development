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
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.databinding.TaskListItemBinding;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;
public class TasksFragment extends Fragment {
    private ArrayList<Task> mTasks = new ArrayList<>();
    LinearLayoutManager layoutManager;
    TasksAdapter adapter;
    RecyclerView recyclerView;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentTasksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTasks = mListener.getAllTasks();

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TasksAdapter(mTasks);
        recyclerView.setAdapter(adapter);


        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllTasks();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textSort = mListener.sortTasks(1);
                adapter.notifyDataSetChanged();
                binding.textViewSortIndicator.setText(textSort);
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textSort = mListener.sortTasks(-1);
                adapter.notifyDataSetChanged();
                binding.textViewSortIndicator.setText(textSort);
            }
        });
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void clearAllTasks();
        String sortTasks(int sortType);
        void deleteTask(Task task, String back);
    }

    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{
        ArrayList<Task> tasks;
        public TasksAdapter(ArrayList<Task> data) {
            this.tasks = data;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskListItemBinding mBinding = TaskListItemBinding.inflate(getLayoutInflater(), parent, false);
            TaskViewHolder holder = new TaskViewHolder(mBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = tasks.get(position);
            holder.setupUI(task);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder{
            TaskListItemBinding mBinding;
            Task mTask;
            public TaskViewHolder(TaskListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoTaskDetails(mTask);
                    }
                });
            }
            public void setupUI(Task task){
                mTask = task;
                mBinding.textViewName.setText(mTask.getName());
                mBinding.textViewCategory.setText(mTask.getCategory());
                mBinding.textViewPriority.setText(mTask.getPriorityStr());

                mBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteTask(mTask, "noback");
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}