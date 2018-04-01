package com.example.user1.urnextapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< Updated upstream
import android.text.TextUtils;
=======
>>>>>>> Stashed changes
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< Updated upstream
import android.widget.ProgressBar;
=======

>>>>>>> Stashed changes
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

<<<<<<< Updated upstream
public class forgotPassword extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
=======
// create class to allow the user to change his password
public class forgotPassword extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;
    private TextView signin;
    private TextView signup;
>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = (EditText) findViewById(R.id.editTextresetemail);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
<<<<<<< Updated upstream
=======
        signin = (TextView)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);
>>>>>>> Stashed changes
        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
<<<<<<< Updated upstream

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                AccessibilityNodeInfo inputPassword = null;
=======
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[com]+";
                AccessibilityNodeInfo inputPassword = null;

                // check if empty and check the format
>>>>>>> Stashed changes
                if (email.isEmpty()) {
                    inputEmail.setError("Please enter your email");
                }
                else if(!email.matches(emailPattern)){
                    inputEmail.setError("Invalid email address");
                }
                else if (email.isEmpty()) {
                    inputPassword.setError("Please enter your password");
                }
                else if (email.length()<5) {
                    inputPassword.setError("Password must to be more than 9 characters");
                }

<<<<<<< Updated upstream
                auth.sendPasswordResetEmail(email)

=======
                // call password reset method with alert
                auth.sendPasswordResetEmail(email)
>>>>>>> Stashed changes
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(forgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(forgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
<<<<<<< Updated upstream
=======
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(forgotPassword.this, PSignUpPage.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(forgotPassword.this, SignInPage.class);
                startActivity(ii);
            }
        });
>>>>>>> Stashed changes

    }
}
