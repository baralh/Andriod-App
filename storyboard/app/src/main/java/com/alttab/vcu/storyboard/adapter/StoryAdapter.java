package com.alttab.vcu.storyboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.res.Resources;
import com.alttab.vcu.storyboard.R;
import com.alttab.vcu.storyboard.model.Story;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Recycler View adapter for list of stories
 */

public class StoryAdapter extends FirestoreAdapter<StoryAdapter.ViewHolder> {

    public interface OnStorySelectedListener {
        void onStorySelected(DocumentSnapshot story);
    }

    private OnStorySelectedListener listener;

    public StoryAdapter(Query query, OnStorySelectedListener listener) {
        super(query);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.sorty_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), listener);
    }


    static class ViewHolder extends  RecyclerView.ViewHolder {
        @BindView(R.id.textCardStoryName)
        TextView storyNameView;

        @BindView(R.id.textCardStoryDate)
        TextView storyDateView;

        @BindView(R.id.textCardStoryText)
        TextView storyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnStorySelectedListener listener) {

            Story story = snapshot.toObject(Story.class);
            Resources resources = itemView.getResources();

            storyNameView.setText(story.getName());
            if (story.getTimestamp() != null) {
                storyDateView.setText(story.getTimestamp().toString());
            } else {
                storyDateView.setText("");
            }

            storyTextView.setText(story.getText());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onStorySelected(snapshot);
                    }
                }
            });
        }


    }


}
