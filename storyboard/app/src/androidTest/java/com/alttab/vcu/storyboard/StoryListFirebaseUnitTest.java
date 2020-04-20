package com.alttab.vcu.storyboard;

import android.support.annotation.NonNull;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.alttab.vcu.storyboard.adapter.StoryAdapter;
import com.alttab.vcu.storyboard.model.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class StoryListFirebaseUnitTest {

    private FirebaseFirestore firestore;

    @Before
    public void setUp() throws Exception {


        firestore = FirebaseFirestore.getInstance();


    }

    @Test
    public void getAllStories_checkNotNull_firstRecord() throws Exception {


        Query query = firestore.collection("stories")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .limit(5);
        StoryAdapter sa = new StoryAdapter(query,new StoryAdapter.OnStorySelectedListener() {
            @Override
            public void onStorySelected(DocumentSnapshot story) {
            }
        } ) {
            @Override
            protected void onDataChanged() {

                    assertTrue("There are some data in the db ",getItemCount() > 0 );

            }
            };
        sa.startListening();

    }
}