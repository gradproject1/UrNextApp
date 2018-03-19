package com.example.user1.urnextapp;
//android
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//fire base
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInPage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference ;
    FirebaseAuth.AuthStateListener mAuthListener;

    EditText inputEmail;
    EditText inputPassword;
    Button sign_in;
    TextView sign_up;
    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        inputEmail = (EditText)findViewById(R.id.editTextEmailAddress);
        inputPassword = (EditText)findViewById(R.id.editText_Password);
        sign_in = (Button)findViewById(R.id.button3);
        sign_up = (TextView)findViewById(R.id.textView3);
        forgotPassword = (TextView)findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String e = inputEmail.getText().toString();
                final String p = inputPassword.getText().toString();
                // pattern for validate the email format
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailAdminPattern = "[HAE]+[0-9]+@[a-z]+\\.+[a-z]+"; //admin email format
                final String emailDoctorPattern = "[HDE]+[0-9]+@[a-z]+\\.+[a-z]+"; //doctor email format
                final String emailNursePattern = "[HNE]+[0-9]+@[a-z]+\\.+[a-z]+"; //nurse email format
// check if email not empty and check email format
                if (e.isEmpty()) {
                    inputEmail.setError("Please enter your email");
                }
                else if(!e.matches(emailPattern)){
                    inputEmail.setError("Invalid email address");
                }
                else if (p.isEmpty()) {
                    inputPassword.setError("Please enter your password");
                }
                else if (p.length()<9) {
                    inputPassword.setError("Password must to be more than 9 characters");
                }
                else { //start sign in
                    firebaseAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                          // if user verify his email allow him to login else send email for verification
                                if(user.isEmailVerified()){
                                //depend on email format open specific page
                                     if(e.matches(emailAdminPattern)){
                                         Intent i = new Intent(SignInPage.this, Admin.class);
                                         startActivity(i);
                                     }
                                     else if(e.matches(emailDoctorPattern)){
                                         Intent i = new Intent(SignInPage.this, Doctor.class);
                                         startActivity(i);
                                     }
                                     else if(e.matches(emailNursePattern)){
                                         Intent i = new Intent(SignInPage.this, Nurse.class);
                                         startActivity(i);
                                     }
                                     else {
                                         Intent i = new Intent(SignInPage.this, Patient.class);
                                         startActivity(i);
                                     }
                                }
                                else if (!user.isEmailVerified()) {
                                     Toast.makeText(SignInPage.this, "Please confirm your email", Toast.LENGTH_SHORT).show();
                                     user.sendEmailVerification();
                                 }
                            }
                            if (!task.isSuccessful()) {

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { // exception of login
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(SignInPage.this, "This user not found, create a new account", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignInPage.this, "The password is invalid, please try valid password", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseNetworkException) {
                                Toast.makeText(SignInPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //open sign up if user dos'nt have an account
                Intent i = new Intent(SignInPage.this, PSignUpPage.class);
                startActivity(i);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //allow the user t change his password
                Intent ii = new Intent(SignInPage.this, forgotPassword.class);
                startActivity(ii);
            }
        });
    }
}