package com.alttab.vcu.storyboard;



import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alttab.vcu.storyboard.data.DataSingle;
import com.alttab.vcu.storyboard.model.Profile;
import com.alttab.vcu.storyboard.utils.ProfileUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends BaseActivity implements ProfileUtils.ProfileReady {

    public static final String TAG = "MainActivity";

    private Button unlockbutton;
    private FirebaseAuth mathAuth;
    DataSingle ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shouldStartSignIn()) {
            // if user is not signed in start sign in but the menu menu is enabled to browse
            startSignIn();
            return;
        } else {
            // if user is already signed in got to list directly
            if (DataSingle.getInstance().getProfile() == null) {
                // we need to set the profile
                setDsProfile();
            }


            Intent intent1 = new Intent(this, StoryListActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
        }

        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(R.string.storyboard);

        }

        mathAuth = FirebaseAuth.getInstance(); //fire base support

    }

    private void setDsProfile() {
        String userId = ProfileUtils.getUser().getUid();
        if (userId != null) {
            ProfileUtils.getProfile(userId,this);
        }
    }

    @Override
    public  void onStart() {
        super.onStart();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {

                Log.d(TAG,"Logging Successful");
                Intent intent = new Intent(this, StoryListActivity.class);
                startActivity(intent);

            } else {

                Log.d(TAG,"Login failed ");
                Toast toast = Toast.makeText(this, "Login Failed!",Toast.LENGTH_SHORT);
                toast.show();


            }

        }


    }

    //check if user has signed in
    // TODO: put signed in flag in ViweModel
    //


    //sign in with firebase google
    public void startSignIn() {

        Intent signInIntent = new Intent(this, GoogleSignIn.class);
        signInIntent.putExtra("action","signIn");
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }


    @Override
    public void onProfileReady(Profile result) {
        DataSingle.getInstance().setProfile(result);
    }
}
