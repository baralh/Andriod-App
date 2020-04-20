package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alttab.vcu.storyboard.data.DataSingle;
import com.alttab.vcu.storyboard.model.Chunk;
import com.alttab.vcu.storyboard.model.Profile;
import com.alttab.vcu.storyboard.model.Story;
import com.alttab.vcu.storyboard.utils.ProfileUtils;
import com.alttab.vcu.storyboard.utils.StoryUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryDetails extends BaseActivity implements EventListener<DocumentSnapshot>, OnSuccessListener<QuerySnapshot> {
    static Button ProfileEdit;
    private static final String TAG = "StoryDetails";
    public static String key_story_id = "key_story_id";

    @BindView(R.id.storydetail_storyChunks)
    TextView storyChunks;
    @BindView(R.id.storydetail_storyTitle)
    TextView storyTitle;
    @BindView(R.id.storydetail_storyDate)
    TextView storyDate;
    @BindView(R.id.storydetail_storyUserId)
    TextView storyUserId;
    @BindView(R.id.storydetail_sendBut)
    Button sendBut;
    @BindView(R.id.storydetail_addChunk)
    EditText addChunk;

    FirebaseFirestore myFireStore;
    FirebaseAuth fa;
    DocumentReference storyRef;
    private ListenerRegistration storyRegister;
    private Query chunksRef;
    Story story ;
    DataSingle ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.storyboard);
        }
        fa = FirebaseAuth.getInstance();
        ds = DataSingle.getInstance();

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vvV) {
                if ( !addChunk.getText().equals("")) {
                    sendChunk(addChunk.getText().toString());
                }

            }
        });

        String storyId = getIntent().getExtras().getString(key_story_id);

        myFireStore = FirebaseFirestore.getInstance();

        storyRef = myFireStore.collection("stories").document(storyId);

    }

    //send to new chunks to db
    private void sendChunk(String text) {
        Chunk chunk = new Chunk();
        chunk.setText(text);
        chunk.setUserId(fa.getUid());
        chunk.setUname(ds.getProfile().getfnAme() + " " + ds.getProfile().getLasTname());
        myFireStore.collection("stories").document(storyRef.getId()).collection("chunck").add(chunk);

    }


    @Override
    protected void onStart() {
        super.onStart();
        storyRegister = storyRef.addSnapshotListener(this);

    }

    @Override
    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException exP) {
        Log.d(TAG, "onEvent: " + documentSnapshot.getData());

        onStoryLoaded(documentSnapshot.toObject(Story.class));
    }

    private class MyOnSuccessListener implements OnSuccessListener<QuerySnapshot>, EventListener<QuerySnapshot> {


        private  Story story;

        public MyOnSuccessListener(Story story) {
            this.story = story;
        }

        @Override
        public void onSuccess(QuerySnapshot documentSnapshots) {
            List<Chunk> chunks = documentSnapshots.toObjects(Chunk.class);
            addChunksToStory(this.story , chunks);
        }

        @Override
        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException exP) {
            List<Chunk> chunks = documentSnapshots.toObjects(Chunk.class);
            addChunksToStory(this.story , chunks);
        }
    }

    private void onStoryLoaded(Story story) {
        Log.d(TAG, "onStoryLoaded: " + story);
        // load the chunks
        chunksRef = storyRef.collection("chunck").orderBy("timestamp");
        ListenerRegistration chunksRegister = chunksRef.addSnapshotListener(new MyOnSuccessListener(story));
    }

    @Override
    public void onSuccess(QuerySnapshot documentSnapshots) {


    }

    private void addChunksToStory(Story story, List<Chunk> chunks) {
        this.story = story;
        Log.d(TAG, "addChunksToStory: ");
        story.setChunk(chunks);
        storyChunks.setText(StoryUtils.mergeChunks(story.getChunk()));
        storyDate.setText(story.getTimestamp().toString());
        storyUserId.setText(story.getUname());
        storyTitle.setText(story.getName());


    }

}
