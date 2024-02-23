package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;

public class UsersFragment extends Fragment {
    ListView listView;
    ArrayAdapter<User> userAdapter;
    String textSort = null;

    public UsersFragment() {
        // Required empty public constructor
    }

    public void setTextSort(String textSort){
        this.textSort = textSort;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentUsersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users Fragment");
        ArrayList<User> users = mListener.getUsers();

        Log.d("USERS", "user list: " + users);
        listView = binding.listView;
        userAdapter = new UserAdapter(getActivity(), users);
        listView.setAdapter(userAdapter);

        if(textSort != null){
            binding.textViewSortIndicator.setText(textSort);
        }


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                mListener.gotoUserDetail(user);
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddUser();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSort();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAll();
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    UserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UserFragmentListener) context;
    }

    public interface UserFragmentListener{
        void gotoAddUser();
        void gotoSort();
        void clearAll();
        void gotoUserDetail(User user);
        ArrayList<User> getUsers();
    }
    public class UserAdapter extends ArrayAdapter<User> {

        public UserAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.user_list_item, parent, false);
            }

            User currentUser = getItem(position);

            TextView textViewName = listItemView.findViewById(R.id.textViewName);
            TextView textViewEmail = listItemView.findViewById(R.id.textViewEmail);
            TextView textViewGender = listItemView.findViewById(R.id.textViewGender);
            TextView textViewAge = listItemView.findViewById(R.id.textViewAge);
            TextView textViewState = listItemView.findViewById(R.id.textViewState);
            TextView textViewGroup = listItemView.findViewById(R.id.textViewGroup);

            textViewName.setText(currentUser.getName());
            textViewEmail.setText(currentUser.getEmail());
            textViewGender.setText(currentUser.getGender());
            textViewAge.setText(currentUser.getAge());
            textViewState.setText(currentUser.getState());
            textViewGroup.setText(currentUser.getGroup());

            return listItemView;
        }
    }
}