package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectSortBinding;
import edu.uncc.assignment05.databinding.FragmentUserDetailsBinding;
import edu.uncc.assignment05.models.User;

public class SelectSortFragment extends Fragment {

    public SelectSortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectSortBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Sort Fragment");

        binding.imageViewNameAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("name", 1);
            }
        });
        binding.imageViewNameDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("name", -1);
            }
        });


        binding.imageViewEmailAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("email", 1);
            }
        });
        binding.imageViewEmailDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("email", -1);
            }
        });


        binding.imageViewGenderAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("gender", 1);
            }
        });
        binding.imageViewGenderDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("gender", -1);
            }
        });


        binding.imageViewAgeAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("age", 1);
            }
        });
        binding.imageViewAgeDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("age", -1);
            }
        });


        binding.imageViewStateAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("state", 1);
            }
        });
        binding.imageViewStateDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("state", -1);
            }
        });


        binding.imageViewGroupAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("group", 1);
            }
        });
        binding.imageViewGroupDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sortUsers("group", -1);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }

    SelectSortFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectSortFragmentListener) context;
    }

    public interface SelectSortFragmentListener{
        void cancel();
        void sortUsers(String value, int order);
    }
}