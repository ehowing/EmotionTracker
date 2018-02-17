package com.example.emilyhowing.emotiontracker2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilyhowing on 4/23/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    protected Context context;

    List<Comment> commentList = new ArrayList<>();

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to
    // represent an item.
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final View commentListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comment_list_item, parent, false);
        return new CommentAdapter.ViewHolder(commentListItem);
    }

    //Called by RecyclerView to display the data at the specified position.
    //This method should update the contents of the itemView to reflect the item at the
    // given position.
    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        holder.commentTextView.setText(comment.getText());
        holder.dateTextView.setText("Date: " + comment.getDate());
        holder.timeTextView.setText("Time: " + comment.getTime());
        holder.userIdTextView.setText("User ID: " + comment.getUserId());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    /**
     * The ViewHolder class 'caches' the references to the subviews, so we don't have to look
     * them up each time.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView commentTextView;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView userIdTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
            userIdTextView = (TextView) itemView.findViewById(R.id.userTextView);
        }
    }
}