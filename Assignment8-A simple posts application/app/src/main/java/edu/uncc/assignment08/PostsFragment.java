package edu.uncc.assignment08;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.assignment08.databinding.FragmentPostsBinding;
import edu.uncc.assignment08.databinding.PostRowItemBinding;
import edu.uncc.assignment08.models.Post;
import edu.uncc.assignment08.models.PostResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    int currentPage = 1;
    int lastPage = 1;

    public PostsFragment() {
        // Required empty public constructor
    }

    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        String user_fullname = mListener.getUserName();
        binding.textViewTitle.setText("Hello " + user_fullname + " :)");
        binding.textViewPaging.setText("Loading ...");
        getPosts(currentPage);

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage == 1){
                    Toast.makeText(getActivity(), "This is the first page.", Toast.LENGTH_SHORT).show();
                }else{
                    currentPage -= 1;
                    getPosts(currentPage);
                }
            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage == lastPage){
                    Toast.makeText(getActivity(), "This is the last page.", Toast.LENGTH_SHORT).show();
                }else{
                    currentPage += 1;
                    getPosts(currentPage);
                }
            }
        });

        getActivity().setTitle(R.string.posts_label);
    }

    void getPosts(int currentPage) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.theappsdr.com/posts").newBuilder();
        urlBuilder.addQueryParameter("page", String.valueOf(currentPage));

        String url = urlBuilder.build().toString();
        String token = mListener.getToken();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();
                PostResponse postResponse = gson.fromJson(body, PostResponse.class);
                mPosts = postResponse.posts;

                int totalCount = postResponse.totalCount;
                int pageSize = postResponse.pageSize;
                double dbLastPage = (double) totalCount / pageSize;
                lastPage = (int) Math.ceil(dbLastPage);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postsAdapter = new PostsAdapter(mPosts);
                        binding.recyclerViewPosts.setAdapter(postsAdapter);

                        binding.textViewPaging.setText(currentPage + " / " + lastPage);
                    }
                });
            }
        });
    }

    void deletePost(String postId){
        RequestBody requestBody = new FormBody.Builder()
                .add("post_id", postId)
                .build();

        String token = mListener.getToken();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/delete")
                .post(requestBody)
                .addHeader("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getPosts(currentPage);
            }
        });
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
        ArrayList<Post> mPosts = new ArrayList<>();
        public PostsAdapter(ArrayList<Post> posts) {
            this.mPosts = posts;
        }

        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.setupUI(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            PostRowItemBinding mBinding;
            Post mPost;
            String userId = mListener.getUserId();
            public PostsViewHolder(PostRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post){
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());

                if(post.getCreated_by_uid().equals(userId)){
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Delete Post");
                            builder.setMessage("Are you sure you want to delete this post?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletePost(mPost.post_id);
                                }
                            });
                            builder.setNegativeButton("Cancel", null);
                            builder.show();
                        }
                    });
                }else{
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
        String getToken();
        String getUserName();
        String getUserId();
    }
}