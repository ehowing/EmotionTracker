package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddComment extends AppCompatActivity {

    private static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        //get the userId, so then you can get the email from database
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        final DatabaseReference userEmailRef = mDatabase.getReference("User email/" + userId);

        //get email from database
        userEmailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference commentRef = mDatabase.getReference("Comment");

        //when submit button is clicked, get text from editText and add data to database
        Button submitComment = (Button) findViewById(R.id.submitComment);
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = email;

                EditText commentEditText = (EditText) findViewById(R.id.addCommentEditText);
                String commentStr = commentEditText.getText().toString();

                if(commentStr.length() == 0) {
                    Toast.makeText(AddComment.this, "Please enter comment",
                            Toast.LENGTH_SHORT).show();
                } else {

                    //call currentDate and time methods
                    String date = DateAndTime.getCurrentDate();
                    String time = DateAndTime.getCurrentTime();

                    Comment comment = new Comment(commentStr, date, time, userId);
                    String key = commentRef.push().getKey();

                    //creates new child with the emotion
                    commentRef.child(key).setValue(comment);

                    finish();
                }
            }
        });
    }
}