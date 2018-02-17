package com.example.emilyhowing.emotiontracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//sign up code from
// http://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-
// auth/

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference emailRef = mDatabase.getReference("User email");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //initialize editText
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        //when login button is clicked, go to login activity
        Button goToLogin = (Button) findViewById(R.id.goToLoginButton);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        Button signUpButton = (Button) findViewById(R.id.signInButton);

        //when the user clicks the sign up button, their email/password is authenticated
        signUpButton.setOnClickListener(new View.OnClickListener() {

            String emailText = "";
            String passwordText = "";
            @Override
            public void onClick(View view) {

                emailText = emailEditText.getText().toString();
                passwordText = passwordEditText.getText().toString();

                //if editTexts are empty, make toast message
                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(getApplicationContext(), "Please enter email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(getApplicationContext(), "Please enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //if password isn't long enough, make toast
                if (passwordText.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 " +
                            "characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user with the email and password
                //if not successful, toast message appears. If successful, signs up user and goes to
                //login activity
                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                            //boolean loginUser = false;

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed."
                                            + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    String userId = user.getUid();
                                    Log.d("userID:", userId);

                                    emailRef.child(userId).setValue(emailText);

                                    startActivity(new Intent(SignUpActivity.this,
                                            LogInActivity.class));
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    }