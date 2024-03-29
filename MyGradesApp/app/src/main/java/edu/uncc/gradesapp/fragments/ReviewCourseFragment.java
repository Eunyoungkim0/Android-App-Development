package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentReviewCourseBinding;
import edu.uncc.gradesapp.databinding.ReviewRowItemBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Review;

import java.util.HashMap;

public class ReviewCourseFragment extends Fragment {
    private static final String ARG_PARAM_COURSE= "ARG_PARAM_COURSE";
    private Course mCourse;
    ArrayList<Review> mReviews = new ArrayList<>();
    ReviewsAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ReviewCourseFragment() {
        // Required empty public constructor
    }

    public static ReviewCourseFragment newInstance(Course course) {
        ReviewCourseFragment fragment = new ReviewCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_PARAM_COURSE);
        }
        setHasOptionsMenu(true);
    }

    FragmentReviewCourseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Review Course");

        binding.textViewCourseName.setText(mCourse.getName());
        binding.textViewCourseNumber.setText(mCourse.getNumber());
        binding.textViewCreditHours.setText(String.valueOf(mCourse.getHours()) + " Credit Hours");

        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = binding.editTextReview.getText().toString();
                if(reviewText.isEmpty()){
                    Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    addReview(reviewText);
                }
            }
        });

        getCourseReviews();
    }

    void getCourseReviews(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses")
                .document(mCourse.getCourseId()).collection("reviews")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mReviews.clear();
                        for(QueryDocumentSnapshot document: value){
                            Review review = document.toObject(Review.class);
                            mReviews.add(review);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    void addReview(String reviewText){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("courses").document(mCourse.getCourseId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        addReviewtoExistingCourse(reviewText);
                    } else {
                        addCourse(reviewText);
                    }
                } else {
                    Log.e("demo", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    void addCourse(String reviewText){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference courseRef = db.collection("courses").document(mCourse.getCourseId());

        HashMap<String, Object> data = new HashMap<>();
        data.put("courseId", mCourse.getCourseId());
        data.put("courseName", mCourse.getName());
        data.put("courseNumber", mCourse.getNumber());
        data.put("hours", mCourse.getHours());
        data.put("reviewCount", 0);

        courseRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    addReviewtoExistingCourse(reviewText);
                }
            }
        });
    }

    void addReviewtoExistingCourse(String reviewText){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reviewRef = db.collection("courses").document(mCourse.getCourseId()).collection("reviews").document();

        HashMap<String, Object> reviewData = new HashMap<>();
        reviewData.put("reviewId", reviewRef.getId());
        reviewData.put("review", reviewText);
        reviewData.put("username", mAuth.getCurrentUser().getDisplayName());
        reviewData.put("userId", mAuth.getCurrentUser().getUid());
        reviewData.put("postdate", FieldValue.serverTimestamp());

        reviewRef.set(reviewData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    calculateReviewCount(1);
                }
            }
        });
    }

    void calculateReviewCount(int increment){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("courses").document(mCourse.getCourseId());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                CourseReview courseReview = documentSnapshot.toObject(CourseReview.class);
                int totalCount = courseReview.getReviewCount();

                totalCount += increment;

                HashMap<String, Object> updateData = new HashMap<>();
                updateData.put("reviewCount", totalCount);
                FirebaseFirestore.getInstance().collection("courses").document(mCourse.getCourseId()).update(updateData);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.editTextReview.setText("");
                        binding.editTextReview.clearFocus();
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(binding.editTextReview.getWindowToken(), 0);
                    }
                });
            }
        });
    }

    class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ReviewRowItemBinding itemBinding = ReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ReviewViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
            if (position >= 0 && position < mReviews.size()) {
                holder.setupUI(mReviews.get(position));
            }
        }


        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        class ReviewViewHolder extends RecyclerView.ViewHolder {
            ReviewRowItemBinding itemBinding;
            Review mReview;
            public ReviewViewHolder(ReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            private void setupUI(Review review){
                this.mReview = review;

                this.itemBinding.textViewReview.setText(mReview.getReview());
                this.itemBinding.textViewUserName.setText(mReview.getUsername());
                // Set the created at time
                if(this.mReview.getPostdate() == null) {
                    this.itemBinding.textViewCreatedAt.setText("N/A");
                } else {
                    Date date = this.mReview.getPostdate().toDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    this.itemBinding.textViewCreatedAt.setText(sdf.format(date));
                }

                if(mAuth.getCurrentUser().getUid().equals(this.mReview.getUserId())){
                    this.itemBinding.imageViewDelete.setVisibility(View.VISIBLE);

                    this.itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("courses").document(mCourse.getCourseId()).collection("reviews").document(mReview.getReviewId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    calculateReviewCount(-1);
                                    getCourseReviews();
                                }
                            });
                        }
                    });
                }else{
                    this.itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ReviewCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ReviewCourseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewCourseListener");
        }
    }

    public interface ReviewCourseListener{
        void onSelectionCanceled();
    }
}