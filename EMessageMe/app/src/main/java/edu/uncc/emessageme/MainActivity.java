package edu.uncc.emessageme;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import edu.uncc.emessageme.auth.LoginFragment;
import edu.uncc.emessageme.auth.SignUpFragment;
import edu.uncc.emessageme.fragments.BlockListFragment;
import edu.uncc.emessageme.fragments.CreateMessageFragment;
import edu.uncc.emessageme.fragments.MessageFragment;
import edu.uncc.emessageme.fragments.MessagesFragment;
import edu.uncc.emessageme.fragments.SelectReceiverFragment;
import edu.uncc.emessageme.fragments.UserListFragment;
import edu.uncc.emessageme.models.Message;
import edu.uncc.emessageme.models.User;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.LoginListener, SignUpFragment.SignUpListener,
        MessagesFragment.MessagesListener, CreateMessageFragment.CreateMessageListener, SelectReceiverFragment.SelectReceiverListener,
        MessageFragment.MessageListener, UserListFragment.UserListListener, BlockListFragment.BlockListListener{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new MessagesFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MessagesFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateMessage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateMessageFragment(), "create-message-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoMessageDetail(Message message) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, MessageFragment.newInstance(message))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoViewUsers() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new UserListFragment(), "user-list-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoBlockList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new BlockListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoReply(Message message) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, CreateMessageFragment.newInstance(message))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectReceiver() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SelectReceiverFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitReceiver(User receiver) {
        CreateMessageFragment fragment = (CreateMessageFragment) getSupportFragmentManager().findFragmentByTag("create-message-fragment");
        if(fragment != null){
            fragment.getReceiver = receiver;
            Log.d("demo", "MainActivity: fragment.getReceiver = receiver; done");
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneSendingMessage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MessagesFragment())
                .commit();
    }

}