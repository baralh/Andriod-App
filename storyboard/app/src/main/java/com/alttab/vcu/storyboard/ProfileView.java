package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alttab.vcu.storyboard.data.DataSingle;
import com.alttab.vcu.storyboard.model.Profile;
import com.alttab.vcu.storyboard.utils.ProfileUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileView extends BaseActivity implements ProfileUtils.ProfileReady {
    static Button ProfileEdit;
    private static final String TAG = "ProfileView";

    @BindView(R.id.ProfileFname)
    TextView profileFname;
    @BindView(R.id.ProfileLname)
    TextView profileLname;
    @BindView(R.id.ProfileBio)
    TextView profileBio;
    @BindView(R.id.ProfileEmail)
    TextView profileEmail;
    @BindView(R.id.ProfileLocation)
    TextView profileLocation;
    @BindView(R.id.imageView1)
    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        ButterKnife.bind(this);
        ProfileEdit = (Button)findViewById(R.id.ProfileEdit);
        ProfileEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent toGameIntent = new Intent(ProfileView.this, ProfileActivity.class);
                startActivity(toGameIntent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        getProfile();
    }


    private void getProfile() {

        //ProfileUtils.getProfile("8wNGTesjqle9f6gNnnUyYaVvaYH2",this);
        String userId = ProfileUtils.getUser().getUid();
        if (userId != null) {
            ProfileUtils.getProfile(userId,this);
        }

    }


    @Override
    public void onProfileReady(Profile result) {
        Log.d(TAG, "onProfileReady: " + result);
        DataSingle ds = DataSingle.getInstance();
        ds.setProfile(result);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        profileFname.setText(result.getfnAme());
        profileLname.setText(result.getLasTname());
        profileBio.setText(result.getmyBio());
        profileEmail.setText(result.getmyEmail());
        profileLocation.setText(result.getmyLocation());

    }
}
