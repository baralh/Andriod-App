package com.alttab.vcu.storyboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageView;

import com.alttab.vcu.storyboard.data.DataSingle;
import com.alttab.vcu.storyboard.model.Chunk;
import com.alttab.vcu.storyboard.model.Story;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.LinkedList;
import java.util.List;


public class CreateMyStory extends BaseActivity {
    Button submitbutton;
    EditText title;
    EditText story;
    final Context context = this;

    private FirebaseFirestore firestore;
    private FirebaseAuth fbAuth;

    DataSingle ds;

    interface StoryListener {
        void onStory(Story story);
    }


    public void addStory(final Story story, final Chunk chunk) {
        firestore = FirebaseFirestore.getInstance();
        final DocumentReference storyRef = firestore.collection("stories").document();
        //        storyRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        //            @Override
        //            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
        //                Log.d(TAG, "onEvent: " + documentSnapshot.getId());
        //
        //
        //                chunkDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        //                    @Override
        //                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
        //                        Log.d(TAG, "onEvent: "+documentSnapshot);
        //                        Intent toGameIntent = new Intent(CreateMyStory.this, StoryListActivity.class);
        //                        startActivity(toGameIntent);
        //                    }
        //                });
        //                chunkDocRef.set(chunk);
        //
        //
        //            }
        //        });
        storyRef.set(story).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                CollectionReference chunkColRef = storyRef.collection("chunck");
                DocumentReference chunkDocRef = chunkColRef.document();
                chunkDocRef.set(chunk).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Intent toGameIntent = new Intent(CreateMyStory.this, StoryListActivity.class);
                        startActivity(toGameIntent);
                    }
                });
            }
        });


    }

    public void onStory(Story story, Chunk chunk) {
        addStory(story, chunk);

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_my_story);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.storyboard);
        }

        fbAuth = FirebaseAuth.getInstance();
        ds = DataSingle.getInstance();

        submitbutton = (Button)findViewById(R.id.submitbutton);
        title = (EditText) findViewById(R.id.createstory_title);
        story = (EditText) findViewById(R.id.createstory_text);



        submitbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final String myTitle = title.getText().toString();
                final String myStory = story.getText().toString();

                if (myTitle.matches("")) {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(context);
                    myAlert
                            .setMessage("Please enter a title!")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = myAlert.create();
                    alertDialog.show();

                } else if (myStory.matches("")) {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(context);
                    myAlert
                            .setMessage("Please enter a story!")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = myAlert.create();
                    alertDialog.show();

                } else {
                    submitbutton.setEnabled(true);
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(context);
                    ImageView img = (ImageView) findViewById(R.id.ImageView01);
                    img.setImageResource(R.drawable.firework);
                    myAlert
                            .setMessage("Congrats! What would you like to do next?")
                            .setPositiveButton("Submit Story", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final Story story = new Story();
                                    Chunk chunk = new Chunk();
                                    chunk.setText(myStory);
                                    chunk.setUserId(fbAuth.getUid());
                                    chunk.setUname(ds.getProfile().getfnAme() + " " + ds.getProfile().getLasTname());
                                    story.setText(myStory);
                                    story.setName(myTitle);
                                    story.setUid(fbAuth.getUid());
                                    story.setUname(ds.getProfile().getfnAme() + " " + ds.getProfile().getLasTname());
                                    onStory(story, chunk);

                                }
                            })
                            .setNegativeButton("Continue Editing", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = myAlert.create();
                    alertDialog.show();
                }
            }
        });

    }

    public boolean checkLength(String text, int len) {
        return text.length() <= len;
    }

}
