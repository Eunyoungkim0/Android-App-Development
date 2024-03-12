package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {
    private String priority = null, category = null;
    private int intPriority = 0;
    private Task mTask;
    public void setPriority(String priority){
        this.priority = priority;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setIntPriority(int intPriority){
        this.intPriority = intPriority;
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(priority == null){
            binding.textViewPriority.setText("N/A");
        } else {
            binding.textViewPriority.setText(priority);
        }

        if(category == null){
            binding.textViewCategory.setText("N/A");
        } else {
            binding.textViewCategory.setText(category);
        }

        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPriority();
            }
        });

        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_name = binding.editTextName.getText().toString();
                String usr_priority = binding.textViewPriority.getText().toString();
                String usr_category = binding.textViewCategory.getText().toString();

                if(usr_name.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                }else if(usr_priority.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select priority", Toast.LENGTH_SHORT).show();
                }else if(usr_category.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_SHORT).show();
                }else{
                    int intPriority = 0;
                    if(usr_priority.equals("Very High")){
                        intPriority = 5;
                    }else if(usr_priority.equals("High")){
                        intPriority = 4;
                    }else if(usr_priority.equals("Medium")){
                        intPriority = 3;
                    }else if(usr_priority.equals("Low")){
                        intPriority = 2;
                    }else if(usr_priority.equals("Very Low")){
                        intPriority = 1;
                    }

                    //String name, String category, String priorityStr, int priority
                    Task task = new Task(usr_name, usr_category, usr_priority, intPriority);
                    mListener.submitAddingNewTask(task);
                }
            }
        });
    }

    AddTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }

    public interface AddTaskListener{
        void gotoSelectPriority();
        void gotoSelectCategory();
        void submitAddingNewTask(Task task);
    }
}