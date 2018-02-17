package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//login code from
//http://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-
// auth/

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        //initializes the editTexts and login button
        setContentView(R.layout.activity_log_in);

        //initialize editTexts and button
        final EditText emailEditText = (EditText) findViewById(R.id.emailLoginEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordLoginEditText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        //when button is clicked, user is logged in
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set string to the email and the password
                String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                //if the editText is empty, make toast message
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter an email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //If sign in fails, display a message to the user.
                                //If login is successful, go to main activity
                                if (!task.isSuccessful()) {

                                    //there was an error
                                    if (password.length() < 6) {
                                        passwordEditText.setError("need longer password");
                                    } else {
                                        Toast.makeText(LogInActivity.this, "Authentication failed",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    Intent intent = new Intent(LogInActivity.this,
                                            AddCommentOrEmotionActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}