package com.alttab.vcu.storyboard;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alttab.vcu.storyboard.adapter.StoryAdapter;
import com.alttab.vcu.storyboard.viewmodel.StoryListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import butterknife.BindFloat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryListActivity extends BaseActivity implements StoryAdapter.OnStorySelectedListener {

    private static final String TAG = "StoryListActivity";

    private static final int RC_SIGN_IN = 9001;

    private static final int LIMIT = 50;

    @BindView(R.id.recycler_stories)
    RecyclerView storiesRecycler;

    @BindView(R.id.create_story_fab)
    FloatingActionButton createStory;


    private FirebaseFirestore firestore;
    private Query query;

    private StoryAdapter adapter;


    private StoryListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stoty_list);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.storyboard);
        }
        // User can not add new if not logged in
        if ( shouldStartSignIn() ) {
            createStory.setVisibility(View.GONE);
        } else {
            createStory.setVisibility(View.VISIBLE);
        }
        createStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewIntent = new Intent(getBaseContext(), CreateMyStory.class);
                startActivity(createNewIntent);
            }
        });

        // View Model
        viewModel = ViewModelProviders.of(this).get(StoryListViewModel.class);

        //Enable Firebase Logging
        FirebaseFirestore.setLoggingEnabled(true);

        //FireStone
        firestore = FirebaseFirestore.getInstance();

        //Get ${LIMIT} stories '
        query = getAllStories();

        //Recycle View
        adapter = new StoryAdapter(query, this) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    storiesRecycler.setVisibility(View.GONE);
                //mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    storiesRecycler.setVisibility(View.VISIBLE);
                    //mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException err) {
                // TODO: Show a snackbar on errors
                //
                Log.d(TAG, "Error: check logs for info." );
                //Snackbar.make(findViewById(android.R.id.content),
                //"Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        storiesRecycler.setLayoutManager(new LinearLayoutManager(this));
        storiesRecycler.setAdapter(adapter);

    }

    @Override
    public  void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //tries to get the current user

        if (user != null) { //if null no current user
            Log.d("Auth", "user signed in");
        }

        if (adapter != null) {
            adapter.startListening();
        }

    }


    @Override
    public void onStorySelected(DocumentSnapshot story) {

        Log.d(TAG, String.format("StorySelected : %S",story.getId()));
        Intent intent = new Intent(this, StoryDetails.class);
        intent.putExtra(StoryDetails.key_story_id, story.getId());
        startActivity(intent);

    }

    public Query getAllStories() {
        return firestore.collection("stories")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .limit(LIMIT);
    }


}
