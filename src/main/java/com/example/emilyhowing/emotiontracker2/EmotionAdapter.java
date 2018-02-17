package com.example.emilyhowing.emotiontracker2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilyhowing on 4/20/17.
 */

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder> {

    protected Context context;

    List<Emotion> emotionList = new ArrayList<>();

    public EmotionAdapter(Context context, List<Emotion> emotionList) {
        this.emotionList = emotionList;
        this.context = context;
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent
    // an item.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final View emotionListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.emotion_list_item, parent, false);
        return new ViewHolder(emotionListItem);
    }

    //Called by RecyclerView to display the data at the specified position.
    //This method should update the contents of the itemView to reflect the item at the
    // given position.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Emotion emotion = emotionList.get(position);

        if ((emotion.getEmotion() != null) && (emotion.getDate() != null) &&
                (emotion.getTime() != null) && (emotion.getColor() != null) &&
                (emotionList.size() > 0)) {

            holder.emotionTextView.setText("   " + emotion.getEmotion());
            holder.dateTextView.setText("Date: " + emotion.getDate());
            holder.timeTextView.setText("Time: " + emotion.getTime());
            String color = emotion.getColor();

            //set color of emotion text box
            if (color.equals("red")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_red_dark);
            } else if (color.equals("orange")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_orange_dark);
            } else if (color.equals("yellow")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_orange_light);
            } else if (color.equals("green")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_green_dark);
            } else if (color.equals("blue")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_blue_bright);
            } else if (color.equals("purple")) {
                holder.emotionTextView.setBackgroundResource(android.R.color.holo_purple);
            }
        }
    }


    @Override
    public int getItemCount() {
        return emotionList.size();
    }

    /**
     * The ViewHolder class 'caches' the references to the subviews, so we don't have to look
     * them up each time.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView emotionTextView;
        public TextView dateTextView;
        public TextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            emotionTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
        }
    }
}