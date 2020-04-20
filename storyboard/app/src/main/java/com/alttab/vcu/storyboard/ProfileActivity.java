package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alttab.vcu.storyboard.data.DataSingle;
import com.alttab.vcu.storyboard.model.Profile;
import com.alttab.vcu.storyboard.model.Story;
import com.alttab.vcu.storyboard.utils.ProfileUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;


public class ProfileActivity extends BaseActivity {


    private Button proFile;
    private EditText name;
    private EditText lname;
    private EditText email;
    private EditText bio;
    private EditText location;

    Profile profile;


    private FirebaseFirestore firestore;

    interface ProfileListener {
        void onProfile(Profile profile);
    }



    public void addStory(final Profile profile) {

        firestore = FirebaseFirestore.getInstance();
        final DocumentReference profileRef = firestore.collection("Profile").document(profile.getId());
        profileRef.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                // got to profile page
                Intent intent3 = new Intent(getApplicationContext(), ProfileView.class);
                startActivity(intent3);
                finish();
            }
        });

        //        return firestore.runTransaction(new Transaction.Function<Void>() {
        //
        //            @Override
        //            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
        //                transaction.set(profileRef, profile);
        //                return null;
        //            }
        //
        //        });

    }

    public void onProfile(Profile profile) {
        addStory(profile);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.storyboard);
        }
        DataSingle ds = DataSingle.getInstance();
        this.profile = ds.getProfile();

        name = (EditText) findViewById(R.id.editText1);
        lname = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        bio = (EditText) findViewById(R.id.editText4);
        location = (EditText) findViewById(R.id.editText5);
        proFile = (Button) findViewById(R.id.Profile_Update);

        if (profile != null) {

            name.setText(this.profile.getfnAme());
            lname.setText(this.profile.getLasTname());
            email.setText(this.profile.getmyEmail());
            bio.setText(this.profile.getmyBio());
            location.setText(this.profile.getmyLocation());

        }

        proFile.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View viewV) {
                if (name.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                        || bio.getText().toString().isEmpty()
                        || location.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Your bio", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Name -  "
                            + name.getText().toString() + " \n" + "Last Name -  " + lname.getText().toString()
                            + " \n" + "E-Mail -  " + email.getText().toString() + " \n" + "Bio -  " + bio.getText().toString()
                            + " \n" + "Location -  " + location.getText().toString(), Toast.LENGTH_SHORT).show();



                    Profile newProfile = new Profile();
                    newProfile.setUserId(ProfileUtils.getUser().getUid());
                    newProfile.setfnAme(name.getText().toString());
                    newProfile.setLasTname(lname.getText().toString());
                    newProfile.setmyEmail(email.getText().toString());
                    newProfile.setmyBio(bio.getText().toString());
                    newProfile.setmyLocation(location.getText().toString());
                    newProfile.setId(profile.getId());
                    onProfile(newProfile);


                }
            }
        });
    }
}