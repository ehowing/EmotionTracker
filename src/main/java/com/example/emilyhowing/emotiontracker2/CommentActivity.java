package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.emilyhowing.emotiontracker2.R.id.recyclerView;
import static com.example.emilyhowing.emotiontracker2.R.id.recyclerViewComment;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    private DatabaseReference usersRef;
    private DatabaseReference fillRecyclerView;

    List<Comment> userComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = (RecyclerView) findViewById(recyclerViewComment);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        commentAdapter = new CommentAdapter(this, userComments);

        recyclerView.setAdapter(commentAdapter);

        //addListenerForSingleValueEvent fills comment recyclerView when activity is started. It
        // iterates through the data snapshot and adds the data to a list of emotion which will be
        // used to fill the recyclerView

        fillRecyclerView = FirebaseDatabase.getInstance().getReference().child("Comment");
        fillRecyclerView.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String key = userSnapshot.getKey();

                    String commentStr = dataSnapshot.child(key).child("text").getValue(String.class);
                    String date = dataSnapshot.child(key).child("date").getValue(String.class);
                    String time = dataSnapshot.child(key).child("time").getValue(String.class);
                    String userId = dataSnapshot.child(key).child("userId").getValue(String.class);

                    userComments.add(new Comment(commentStr, date, time, userId));
                }

                //last item is added twice, so have to remove the last item once
                if(userComments.size() > 0) {
                    userComments.remove(userComments.size() - 1);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //addValueEventListener used to update recyclerView when comment is added
        usersRef = FirebaseDatabase.getInstance().getReference().child("Comment");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String commentStr = "";
                String date = "";
                String time = "";
                String userId = "";

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String key = userSnapshot.getKey();

                    commentStr = dataSnapshot.child(key).child("text").getValue(String.class);
                    date = dataSnapshot.child(key).child("date").getValue(String.class);
                    time = dataSnapshot.child(key).child("time").getValue(String.class);
                    userId = dataSnapshot.child(key).child("userId").getValue(String.class);
                }

                userComments.add(new Comment(commentStr, date, time, userId));
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //when floating action button is pressed, go to the addComment activity
        FloatingActionButton inputEmotionFab = (FloatingActionButton) findViewById
                (R.id.addCommentButton);
        inputEmotionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommentActivity.this, AddComment.class);
                startActivity(intent);
            }
        });

        //when go to emotion fab is clicked, go to the main activity
        FloatingActionButton goToEmotionButton = (FloatingActionButton) findViewById
                (R.id.goToEmotionScreen);
        goToEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //when log out button is clicked, log user out and go the the sign up activity
        Button logOutButtonComment = (Button) findViewById(R.id.logOutButtonComment);
        logOutButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CommentActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}