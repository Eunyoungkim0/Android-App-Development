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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.CourseReviewRowItemBinding;
import edu.uncc.gradesapp.databinding.FragmentCourseReviewsBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Favorited;
import edu.uncc.gradesapp.models.Review;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseReviewsFragment extends Fragment {
    public CourseReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentCourseReviewsBinding binding;
    ArrayList<CourseReview> mCourseReviews = new ArrayList<>();
    ArrayList<Course> mCourses = new ArrayList<>();
    CourseReviewsAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Course Reviews");
        adapter = new CourseReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        getCourses();
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getCourses(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cci-courses")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mCourses.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("courses");
                        for (int i = 0; i < courses.length(); i++) {
                            Course course = new Course(courses.getJSONObject(i));
                            mCourses.add(course);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCoursesCollection();
//                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    void getCoursesCollection() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mCourseReviews.clear();
                        int totalTasks = value.size(); // Track the total number of tasks
                        AtomicInteger completedTasks = new AtomicInteger(0); // Track completed tasks
                        for (QueryDocumentSnapshot document : value) {
                            CourseReview courseReview = document.toObject(CourseReview.class);

                            db.collection("courses").document(courseReview.getCourseId())
                                    .collection("favorited").document(mAuth.getCurrentUser().getUid()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    courseReview.setFavorite(true);
                                                }
                                            } else {
                                                Log.d("demo", "Error getting documents: ", task.getException());
                                            }
                                            int count = completedTasks.incrementAndGet();
                                            if (count == totalTasks) {
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                            mCourseReviews.add(courseReview);
                        }
                    }
                });
    }

    class CourseReviewsAdapter extends RecyclerView.Adapter<CourseReviewsAdapter.CourseReviewsViewHolder> {

        @NonNull
        @Override
        public CourseReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CourseReviewRowItemBinding itemBinding = CourseReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CourseReviewsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseReviewsViewHolder holder, int position) {
            if (position >= 0 && position < mCourses.size()) {
                holder.setupUI(mCourses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }

        class CourseReviewsViewHolder extends RecyclerView.ViewHolder {
            CourseReviewRowItemBinding itemBinding;
            Course mCourse;
            public CourseReviewsViewHolder(CourseReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
                this.itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoReviewCourse(mCourse);
                    }
                });
            }

            public void setupUI(Course course){
                this.mCourse = course;
                itemBinding.textViewCourseName.setText(course.getName());
                itemBinding.textViewCreditHours.setText(course.getHours() + " Credit Hours");
                itemBinding.textViewCourseNumber.setText(course.getNumber());

                CourseReview courseReview = null;
                boolean favorite = false;
                int reviewCount = 0;

                for (CourseReview review : mCourseReviews) {
                    if (review.getCourseId().equals(course.getCourseId())) {
                        courseReview = review;
                        reviewCount = courseReview.getReviewCount();
                        favorite = courseReview.isFavorite();
//                        Log.d("demo", "courseReview.isFavorite(): " + courseReview.isFavorite());
                        break;
                    }
                }

                itemBinding.textViewCourseReviews.setText(reviewCount + " Reviews");

                if(favorite){
                    itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_full);
                }else{
                    itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                }

                boolean finalFavorite = favorite;
                itemBinding.imageViewHeart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(finalFavorite){
                            deleteFavorite(course.getCourseId());
                        }else{
                            checkCourse(course.getCourseId(), course.getName(), course.getNumber(), course.getHours());
                        }
                    }
                });
            }
        }
    }

    void checkCourse(String courseId, String courseName, String courseNumber, double hours){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("courses").document(courseId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        addFavorite(courseId);
                    } else {
                        addCourse(courseId, courseName, courseNumber, hours);
                    }
                } else {
                    Log.e("demo", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    void addCourse(String courseId, String courseName, String courseNumber, double hours){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference courseRef = db.collection("courses").document(courseId);

        HashMap<String, Object> data = new HashMap<>();
        data.put("courseId", courseId);
        data.put("courseName", courseName);
        data.put("courseNumber", courseNumber);
        data.put("hours", hours);
        data.put("reviewCount", 0);

        courseRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    addFavorite(courseId);
                }
            }
        });
    }

    void addFavorite(String courseId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference favoriteRef = db.collection("courses").document(courseId).collection("favorited").document(userId);

        HashMap<String, Object> favoriteData = new HashMap<>();
        favoriteData.put("userId", userId);

        favoriteRef.set(favoriteData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    getCoursesCollection();
                }
            }
        });
    }

    void deleteFavorite(String courseId){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").document(courseId).collection("favorited").document(mAuth.getCurrentUser().getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    getCoursesCollection();
                }
            }
        });
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

    CourseReviewsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CourseReviewsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CourseReviewsListener");
        }
    }

    public interface CourseReviewsListener{
        void onSelectionCanceled();
        void gotoReviewCourse(Course course);
    }
}