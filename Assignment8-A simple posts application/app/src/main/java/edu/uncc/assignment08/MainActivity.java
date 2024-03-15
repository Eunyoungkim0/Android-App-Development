package edu.uncc.assignment08;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import edu.uncc.assignment08.models.AuthResponse;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenCheck();
    }

    public void tokenCheck() {
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);

        if (token != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new PostsFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        }
    }

    public String getToken(){
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);

        return token;
    }

    public String getUserName(){
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        String user_fullname = sharedPref.getString("user_fullname", null);

        return user_fullname;
    }

    public String getUserId(){
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("user_id", null);

        return user_id;
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted(AuthResponse authResponse) {
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", authResponse.user_id);
        editor.putString("user_fullname", authResponse.user_fullname);
        editor.putString("token", authResponse.token);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new PostsFragment())
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void logout() {
        SharedPreferences sharedPref = getSharedPreferences("token_check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", null);
        editor.putString("user_fullname", null);
        editor.putString("token", null);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void createPost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }
}