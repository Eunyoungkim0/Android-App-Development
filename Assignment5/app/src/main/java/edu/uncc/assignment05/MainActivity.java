package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectSortFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UserDetailsFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.Data;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity
        implements UsersFragment.UserFragmentListener,
                AddUserFragment.AddUserFragmentListener,
                SelectGenderFragment.SelectGenderFragmentListener,
                SelectAgeFragment.SelectAgeFragmentListener,
                SelectStateFragment.SelectStateFragmentListener,
                SelectGroupFragment.SelectGroupFragmentListener,
                UserDetailsFragment.UserDetailFragmentListener,
                SelectSortFragment.SelectSortFragmentListener{

    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new UsersFragment(), "user-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment(), "add-user-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSortFragment(), "select-sort-fragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void gotoUserDetail(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UserDetailsFragment.newInstance(user), "user-detail-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<User> getUsers() {
        return mUsers;
    }


    @Override
    public void gotoSelectGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGenderFragment(), "select-gender-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectAge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectAgeFragment(), "select-age-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectState() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectStateFragment(), "select-state-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectGroup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGroupFragment(), "select-group-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitGender(String gender) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if(fragment != null){
            fragment.setGender(gender);
        }
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void submitAge(int age) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if(fragment != null){
            fragment.setAge(age);
        }
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void submitState(String state) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if(fragment != null){
            fragment.setState(state);
        }
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void submitGroup(String group) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-fragment");
        if(fragment != null){
            fragment.setGroup(group);
        }
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void submitAddingNewUser(User user) {
        mUsers.add(user);
        this.sortUsers("name", 1);
//        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void clearAll() {
        mUsers.clear();
    }

    @Override
    public void deleteUser(User user) {
        mUsers.remove(user);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sortUsers(String value, int order) {
        String textSort = "Sort by ";

        if(value.equals("name")){
            textSort += "Name";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getName().compareTo(o2.getName());
                }
            });
        }else if(value.equals("email")){
            textSort += "Email";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getEmail().compareTo(o2.getEmail());
                }
            });
        }else if(value.equals("gender")){
            textSort += "Gender";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getGender().compareTo(o2.getGender());
                }
            });
        }else if(value.equals("age")){
            textSort += "Age";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getAge().compareTo(o2.getAge());
                }
            });
        }else if(value.equals("state")){
            textSort += "State";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getState().compareTo(o2.getState());
                }
            });
        }else if(value.equals("group")){
            textSort += "Group";
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return order * o1.getGroup().compareTo(o2.getGroup());
                }
            });
        }

        if(order == 1){
            textSort += " (ASC)";
        }else{
            textSort += " (DESC)";
        }

        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("user-fragment");
        if(fragment != null){
            fragment.setTextSort(textSort);
        }

        getSupportFragmentManager().popBackStack();
    }
}