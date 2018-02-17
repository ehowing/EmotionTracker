package com.example.emilyhowing.emotiontracker2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    //write test
    @Test
    public void addUserAndEmotionTest() throws Exception {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference users = firebaseDatabase.getReference("users");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        users.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Emotion emotion1 = new Emotion("responsible", "04/11/2017", "11:15", "blue");
                users.child("testChildEmotion").setValue(emotion1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        countDownLatch.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void readEmotionTest() throws Exception {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference users2 = firebaseDatabase.getReference("users");
        DatabaseReference emotionRef = firebaseDatabase.getReference("users/testChildEmotion/emotion");

        final CountDownLatch writeSignal = new CountDownLatch(1);

        emotionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Emotion emotion1 = new Emotion("responsible", "04/11/2017", "11:15", "blue");

                users2.child("testChildEmotion").setValue(emotion1);
                assertEquals("responsible",
                        dataSnapshot.getValue(String.class));
                writeSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void readTimeTest() throws Exception
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference users3 = firebaseDatabase.getReference("users");
        DatabaseReference time = firebaseDatabase.getReference("users/testChildTime/time");

        Emotion emotion1 = new Emotion("responsible", "04/11/2017", "11:15", "blue");
        users3.child("testChildTime").setValue(emotion1);

        final CountDownLatch writeSignal = new CountDownLatch(1);

        time.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                assertEquals("11:15", dataSnapshot.getValue(String.class));
                writeSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void readDateTest() throws Exception
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference users4 = firebaseDatabase.getReference("users");
        DatabaseReference date = firebaseDatabase.getReference("users/testChildDate/date");

        Emotion emotion1 = new Emotion("responsible", "04/11/2017", "11:15", "blue");
        users4.child("testChildDate").setValue(emotion1);

        final CountDownLatch writeSignal = new CountDownLatch(1);

        date.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                assertEquals("04/11/2017", dataSnapshot.getValue(String.class));
                writeSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void deleteTest() throws Exception {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference users4 = firebaseDatabase.getReference("users");
        DatabaseReference date = firebaseDatabase.getReference("users/testChildDelete/date");
        final CountDownLatch writeSignal = new CountDownLatch(1);

        date.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Emotion emotion1 = new Emotion("responsible", "04/11/2017", "11:15", "blue");

                users4.child("testChildDelete").setValue(emotion1);

                users4.child("testChildDelete").child("date").setValue(null);

                assertNull(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(1, TimeUnit.SECONDS);
    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.emilyhowing.emotiontracker2", appContext.getPackageName());
    }
}
