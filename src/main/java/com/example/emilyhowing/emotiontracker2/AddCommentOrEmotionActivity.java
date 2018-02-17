package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AddCommentOrEmotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_or_emotion);

        //initialize button and when it's clicked, go to MainActivity
        Button goToEmotionButton = (Button) findViewById(R.id.goToEmotion);
        goToEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCommentOrEmotionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //initialize button and when clicked, go to CommentActivity
        Button goToCommentButton = (Button) findViewById(R.id.goToComment);
        goToCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCommentOrEmotionActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });
    }
}