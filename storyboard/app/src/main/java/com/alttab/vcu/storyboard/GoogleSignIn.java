package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignIn extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "GoogleSignIn";
    private FirebaseAuth mathAuth;
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient myGoogleApiClient;
    private TextView myStatusTextView;
    private TextView myDetailTextView;
    private Button buttonSignIn;
    private Button buttonSignOut;

    AnimationDrawable animation;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        animation = (AnimationDrawable) imageView.getBackground();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.storyboard);
        }

        mathAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build();

        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        buttonSignIn = (Button) findViewById(R.id.button);
        buttonSignOut = (Button) findViewById(R.id.logout);
        final Intent i = new Intent(this, GoogleSignIn.class);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viEw) {
                signOut();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View viEw) {
                signIn();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animation.start();
    }

    private void signOut() {
        //firebase signout
        mathAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(myGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateuI(null);
                        Intent intent = new Intent(getApplicationContext(), StoryListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
    }

    private  void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(myGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override


    public void onStart() {
        super.onStart();

        // check if user signed in

        // Log.d("firebase user", mAuth.getCurrentUser().toString());

        FirebaseUser currentUser = mathAuth.getCurrentUser();
        updateuI(currentUser);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mathAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent data = new Intent();


                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mathAuth.getCurrentUser();
                            updateuI(user);
                            setResult(RESULT_OK);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(GoogleSignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateuI(null);
                            setResult(RESULT_CANCELED);
                        }

                        finish();

                        // ...
                    }
                });
    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    //
    //        final TextView welText = (TextView) findViewById(R.id.welText);
    //        final Button button = (Button) findViewById(R.id.button);
    //        if (requestCode == 1) {
    //            if(resultCode == Activity.RESULT_OK){
    //                String result=data.getStringExtra("result");
    //                Log.v("main", result);
    //               Log.v("main",result);
    //                if(result.equals("1")){
    //                    welText.setText("The App Is Unlocked");
    //                    button.setVisibility(View.GONE);
    //                }else{
    //                    welText.setText("Login Failed");
    //                }
    //            }
    //            if (resultCode == Activity.RESULT_CANCELED) {
    //             Write your code if there's no result
    //            }
    //        }
    //
    //    }

    private  void updateuI(FirebaseUser fu) {

        if (fu == null) {

            buttonSignOut.setVisibility(View.INVISIBLE);
            buttonSignIn.setVisibility(View.VISIBLE);
            Log.d(TAG,"no current user");

        } else {
            buttonSignIn.setVisibility(View.INVISIBLE);
            buttonSignOut.setVisibility(View.VISIBLE);

            Log.d(TAG,String.format("display name %s, email %s",fu.getDisplayName(), fu.getEmail()));

        }
    }

    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
