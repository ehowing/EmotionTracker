package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//notification code from https://www.tutorialspoint.com/android/android_notifications.htm

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmotionAdapter emotionAdapter;

    private DatabaseReference userIdRef;
    private DatabaseReference fillEmotions;

    private static List<Emotion> allEmotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        allEmotions = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        emotionAdapter = new EmotionAdapter(this, allEmotions);
        recyclerView.setAdapter(emotionAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        //fill recyclerView when first opened
            fillEmotions = FirebaseDatabase.getInstance().getReference("users/" + userId);
            fillEmotions.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String emotion = "";
                    String date = "";
                    String time = "";
                    String color = "";

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();

                        emotion = dataSnapshot.child(userId).child("emotion").getValue(String.class);
                        date = dataSnapshot.child(userId).child("date").getValue(String.class);
                        time = dataSnapshot.child(userId).child("time").getValue(String.class);
                        color = dataSnapshot.child(userId).child("color").getValue(String.class);
                        allEmotions.add(new Emotion(emotion, date, time, color));
                    }

                    //the dataSnapshot duplicates the last item, so have remove one of the copies
                    if (allEmotions.size() > 0) {
                        allEmotions.remove(allEmotions.size() - 1);
                    }

                    emotionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        //update recyclerView when data is added
        userIdRef = FirebaseDatabase.getInstance().getReference("users/" + userId);
        userIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String emotion = "";
                String date = "";
                String time = "";
                String color = "";

                //clear previous list of emotion
                allEmotions.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String key = userSnapshot.getKey();

                    emotion = dataSnapshot.child(key).child("emotion").getValue(String.class);
                    date = dataSnapshot.child(key).child("date").getValue(String.class);
                    time = dataSnapshot.child(key).child("time").getValue(String.class);
                    color = dataSnapshot.child(key).child("color").getValue(String.class);
                    allEmotions.add(new Emotion(emotion, date, time, color));
                }

                emotionAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //when input emotion button is clicked, go to add emotion activity
        FloatingActionButton inputEmotionFab = (FloatingActionButton) findViewById
                (R.id.addEmotionFab);
        inputEmotionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEmotionActivity.class);
                startActivity(intent);
            }
        });

        //when go to comment screen button is clicked, go to comment activity
        FloatingActionButton goToCommentScreen = (FloatingActionButton) findViewById
                (R.id.goToCommentScreen);
        goToCommentScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });

        //when logout button is clicked, log user out and go to signUp activity
        Button logOutButtonEmotion = (Button) findViewById(R.id.logOutButtonEmotion);
        logOutButtonEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public static List<Emotion> getAllEmotions(){
        return allEmotions;
    }

}


