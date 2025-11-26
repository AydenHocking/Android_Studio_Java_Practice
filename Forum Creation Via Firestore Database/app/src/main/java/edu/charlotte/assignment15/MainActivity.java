package edu.charlotte.assignment15;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.assignment15.fragments.CreateForumFragment;
import edu.charlotte.assignment15.fragments.ForumFragment;
import edu.charlotte.assignment15.fragments.ForumsFragment;
import edu.charlotte.assignment15.fragments.LoginFragment;
import edu.charlotte.assignment15.fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, SignUpFragment.SignUpListener, ForumsFragment.ForumsListener, CreateForumFragment.AddTaskListener, ForumFragment.ForumListener {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new ForumsFragment())
                    .commit();
        } else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SignUpFragment())
                .commit();
    }

    @Override
    public void loginSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new ForumsFragment())
                .commit();
    }

    @Override
    public void cancelCreateAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoAddForum() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateForumFragment(), "addTaskFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignout() {
        FirebaseAuth.getInstance().signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void onForumClicked(Forum forum) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, ForumFragment.newInstance(forum))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelNewForum() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onForumAdded() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }
}