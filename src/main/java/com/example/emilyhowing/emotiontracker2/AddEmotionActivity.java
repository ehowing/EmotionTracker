package com.example.emilyhowing.emotiontracker2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//spinner code from https://www.tutorialspoint.com/android/android_spinner_control.htm
public class AddEmotionActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mUserRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emotion);

        final EditText emotionEditText = (EditText) findViewById(R.id.emotionEditText);
        final EditText dateEditText = (EditText) findViewById(R.id.dateEditText);
        final EditText timeEditText = (EditText) findViewById(R.id.timeEditText);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        //initialize switch and setting it to false (unchecked)
        final Switch timeSwitch = (Switch) findViewById(R.id.switch1);
        timeSwitch.setChecked(false);

        //when button is clicked, the information in the text fields is put in the database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                String dateString = dateEditText.getText().toString();
                String timeString = timeEditText.getText().toString();
                String emotion1 = emotionEditText.getText().toString();

                //call validDate and time methods to ensure the user input is valid
                boolean validTime = DateAndTime.validTime(timeString);
                boolean validDate = DateAndTime.validDate(dateString);

                if (!validTime) {
                    Toast.makeText(getApplicationContext(), "Invalid time format",
                            Toast.LENGTH_SHORT).show();
                }

                if (!validDate) {
                    Toast.makeText(getApplicationContext(), "Invalid date format",
                            Toast.LENGTH_SHORT).show();
                }

                if (emotion1.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter an emotion",
                            Toast.LENGTH_SHORT).show();
                }

                //get spinner value
                String spinnerText = spinner.getSelectedItem().toString();

                //if all fields are valid, put data in database and make notification
                if (validTime && validDate && emotion1.length() > 0) {

                    //if switch is checked, add pm to the string (for the database),
                    //if unchecked, add am
                    if (timeSwitch.isChecked()) {
                        timeString = timeString + " pm";
                    } else {
                        timeString = timeString + " am";
                    }

                    //gets the key for this child
                    String key = mUserRef.push().getKey();

                    Emotion emotion = new Emotion(emotion1, dateString, timeString, spinnerText);

                    //creates new child with the emotion in database
                    mUserRef.child(userId).child(key).setValue(emotion);

                    finish();
                    addNotification();
                }
            }
        });

        //if currentDateAndTimeButton is clicked, call getCurrentDate and time methods and set
        //textboxes to it
        Button currentDateAndTimeButton = (Button) findViewById(R.id.currentTimeAndDateButton);
        currentDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText dateEditText = (EditText) findViewById(R.id.dateEditText);
                final EditText timeEditText = (EditText) findViewById(R.id.timeEditText);

                dateEditText.setText(DateAndTime.getCurrentDate());

                String timeWithAmPM = DateAndTime.getCurrentTime();
                String timeWithoutAmPm = formatTime(timeWithAmPM);
                timeEditText.setText(timeWithoutAmPm);
            }
        });

        //Spinner Drop down elements
        List<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("orange");
        colors.add("yellow");
        colors.add("green");
        colors.add("blue");
        colors.add("purple");

        //Creates adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colors);

        //set spinner drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attach adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    /**
     * 1. Removes the AM/PM of the date.
     * 2. Switches the switch to PM if the time is after noon; if date is in the morning, the switch
     *    is not switched
     * @param time: current time
     * @return formatted time
     */
    public String formatTime(String time) {

        final Switch timeSwitch = (Switch) findViewById(R.id.switch1);

        //if am, remove am
        //if pm, remove pm and switch the switch to pm
        if (time.substring(time.length() - 2, time.length()).equals("AM")) {
            time = time.substring(0, time.length() - 3);
        } else {
            time = time.substring(0, time.length() - 3);
            timeSwitch.setChecked(true);
        }
        return time;
    }


    /**
     * makes a notification when a user logs an emotion
     */
    private void addNotification() {

        //make notification and set notification properties
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentTitle("You logged an emotion")
                        .setContentText("Keep it up!");

        //Send notification to the system
        NotificationManager manager = (NotificationManager) getSystemService
                (Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}