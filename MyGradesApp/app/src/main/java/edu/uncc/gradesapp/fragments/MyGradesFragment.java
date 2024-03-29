package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentMyGradesBinding;
import edu.uncc.gradesapp.databinding.GradeRowItemBinding;
import edu.uncc.gradesapp.models.Grade;

public class MyGradesFragment extends Fragment {

    public MyGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            mListener.gotoAddGrade();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_reviews){
            mListener.gotoViewReviews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMyGradesBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GradesAdapter gradesAdapter;
    ArrayList<Grade> mGrades = new ArrayList<>();
    double gpa = 4.0,
            total_hours = 0.0,
            total_grade = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gradesAdapter = new GradesAdapter();
        binding.recyclerView.setAdapter(gradesAdapter);
        getActivity().setTitle("My Grades");

        getMyGrades();
    }

    void getMyGrades(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("grades")
                .whereEqualTo("userId", mAuth.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mGrades.clear();
                        gpa = 4.0;
                        total_hours = 0.0;
                        total_grade = 0.0;

                        for(QueryDocumentSnapshot document: value){
                            Grade grade = document.toObject(Grade.class);
                            mGrades.add(grade);
                            total_hours += grade.getCredit();
                            total_grade += grade.getGrade_number() * grade.getCredit();
                        }
                        if(total_hours != 0.0){
                            gpa = total_grade/total_hours;
                        }

                        binding.textViewGPA.setText("GPA : " + String.format("%.1f", gpa));
                        binding.textViewHours.setText("Hours : " + String.format("%.1f", total_hours));
                        gradesAdapter.notifyDataSetChanged();
                    }
                });
    }


    class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradesViewHolder> {
        @NonNull
        @Override
        public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding binding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradesViewHolder extends RecyclerView.ViewHolder {
            GradeRowItemBinding mBinding;
            Grade mGrade;
            public GradesViewHolder(GradeRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Grade grade){
                mGrade = grade;
                mBinding.textViewCourseName.setText(grade.getCourse_name());
                mBinding.textViewSemester.setText(grade.getSemester());
                mBinding.textViewLetterGrade.setText(grade.getGrade_letter());
                mBinding.textViewCourseNumber.setText(grade.getCourse());
                mBinding.textViewCreditHours.setText(grade.getCredit() + " Credit Hours");

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("grades").document(mGrade.getGradeId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                getMyGrades();
                            }
                        });
                    }
                });
            }
        }

    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MyGradesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void logout();
        void gotoViewReviews();
    }
}