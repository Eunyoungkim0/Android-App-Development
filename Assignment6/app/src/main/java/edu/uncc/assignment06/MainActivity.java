package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements
    TasksFragment.TasksListener,
    AddTaskFragment.AddTaskListener,
        SelectPriorityFragment.SelectPriorityListener,
        SelectCategoryFragment.SelectCategoryListener,
        TaskDetailsFragment.TaskDetailFragmentListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment())
                .commit();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), "add-task-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task), "task-detail-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearAllTasks() {
        mTasks.clear();
    }

    @Override
    public String sortTasks(int sortType) {

        String textSort = "Sort by Priority";
        if(sortType == 1){
            textSort += " (ASC)";
        }else{
            textSort += " (DESC)";
        }
        Collections.sort(mTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return sortType * (o1.getPriority() - o2.getPriority());
            }
        });

        return textSort;
    }

    @Override
    public void deleteTask(Task task, String back) {
        mTasks.remove(task);

        if(back.equals("back")){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void gotoSelectPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectPriorityFragment(), "select-priority-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment(), "select-category-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitAddingNewTask(Task task) {
        mTasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitCategory(String category) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add-task-fragment");
        if(fragment != null){
            fragment.setCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitPriority(String priority, int intPriority) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add-task-fragment");
        if(fragment != null){
            fragment.setPriority(priority);
            fragment.setIntPriority(intPriority);
        }
        getSupportFragmentManager().popBackStack();
    }
}