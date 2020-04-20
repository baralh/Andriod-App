package com.alttab.vcu.storyboard.utils;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alttab.vcu.storyboard.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class ProfileUtils {
    private static final String TAG = "ProfileUtil";

    /**
     * get current user and returns the `FirebaseUser` Object or `Null`
     * @return FrebaseUser
     */
    public static FirebaseUser getUser() {

        FirebaseAuth myAuth = FirebaseAuth.getInstance();
        Log.d(TAG,"getUser: " + myAuth.getCurrentUser());
        return myAuth.getCurrentUser();

    }



    public static void getProfile(String userId, final ProfileReady listener) {
        if (userId == null) {
            Log.d(TAG, "getProfile: Uset is null exit");
            return;
        }

        FirebaseFirestore myFirestore = FirebaseFirestore.getInstance();
        myFirestore.collection("Profile")
                .whereEqualTo("userId",userId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Profile profile = new Profile();
                            for (DocumentSnapshot doc: task.getResult()) {
                                profile = doc.toObject(Profile.class);
                                profile.setId(doc.getId());
                            }
                            Log.d(TAG, "onComplete: " + profile);
                            listener.onProfileReady(profile);
                        }
                    }
                });

    }

    public interface ProfileReady {
        void onProfileReady(Profile result);
    }




}
