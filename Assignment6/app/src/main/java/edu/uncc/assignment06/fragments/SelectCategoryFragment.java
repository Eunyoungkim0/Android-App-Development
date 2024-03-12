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

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.databinding.SelectionListItemBinding;
import edu.uncc.assignment06.models.Data;

public class SelectCategoryFragment extends Fragment {
    LinearLayoutManager layoutManager;
    CategoryRecycleViewAdapter adapter;
    RecyclerView recyclerView;
    String[] categories;

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectCategoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories = Data.categories;

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryRecycleViewAdapter(categories);
        recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });
    }

    SelectCategoryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }

    public interface SelectCategoryListener{
        void cancel();
        void submitCategory(String category);
    }

    class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.CategoryViewHolder>{
        String[] categories;

        public CategoryRecycleViewAdapter(String[] categories){
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SelectionListItemBinding mBinding = SelectionListItemBinding.inflate(getLayoutInflater(), parent, false);
            CategoryViewHolder holder = new CategoryViewHolder(mBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            String category = categories[position];
            holder.category = category;
            holder.setupUI(category);
        }

        @Override
        public int getItemCount() {
            if (categories != null) {
                return categories.length;
            } else {
                return 0;
            }
        }

        public class CategoryViewHolder extends RecyclerView.ViewHolder {
            SelectionListItemBinding mBinding;
            String category;
            public CategoryViewHolder(SelectionListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.submitCategory(category);
                    }
                });
            }

            public void setupUI(String category){
                mBinding.textView.setText(category);
            }
        }
    }
}