package com.alttab.vcu.storyboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * RecyclerView adapter for displaying the results of a Firestore {@link Query}.
 * Note that this class forgoes some efficiency to gain simplicity. For example, the result of
 * {@link DocumentSnapshot#toObject(Class)} is not cached so the same object may be deserialized
 * many times as the user scrolls.
 */
public abstract class FirestoreAdapter<ViewT extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewT>
        implements EventListener<QuerySnapshot> {

    private static final String TAG = "FirestoreAdapter";

    private Query myQuery;
    private ListenerRegistration myRegistration;

    private ArrayList<DocumentSnapshot> mySnapshots = new ArrayList<>();

    public FirestoreAdapter(Query query) {
        myQuery = query;
    }

    @Override
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException erroR) {
        if (erroR != null) {
            Log.w(TAG, "onEvent:error", erroR);
            onError(erroR);
            return;
        }

        // Dispatch the event
        Log.d(TAG, "onEvent:numChanges:" + documentSnapshots.getDocumentChanges().size());
        for (DocumentChange change : documentSnapshots.getDocumentChanges()) {

            switch (change.getType()) {
                
              case ADDED:
                  onDocumentAdded(change);
                  break;

              case MODIFIED:
                  onDocumentModified(change);
                  break;
                
              case REMOVED:
                  onDocumentRemoved(change);
                  break;

              default: break;
              
            }
        }

        onDataChanged();
    }

    public void startListening() {
        if (myQuery != null && myRegistration == null) {
            myRegistration = myQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (myRegistration != null) {
            myRegistration.remove();
            myRegistration = null;
        }

        mySnapshots.clear();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mySnapshots.clear();
        notifyDataSetChanged();

        // Listen to new query
        myQuery = query;
        startListening();
    }

    @Override
    public int getItemCount() {
        return mySnapshots.size();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return mySnapshots.get(index);
    }

    protected void onDocumentAdded(DocumentChange change) {
        mySnapshots.add(change.getNewIndex(), change.getDocument());
        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mySnapshots.set(change.getOldIndex(), change.getDocument());
            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mySnapshots.remove(change.getOldIndex());
            mySnapshots.add(change.getNewIndex(), change.getDocument());
            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mySnapshots.remove(change.getOldIndex());
        notifyItemRemoved(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException eeE) {}

    protected void onDataChanged() {}
}