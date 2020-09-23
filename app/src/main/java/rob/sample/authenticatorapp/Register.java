package rob.sample.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone, mconfirmpassword;
    TextView mloginText;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar mprogressBar;

//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//if (user!= null) {
//        // Name, email address, and profile photo Url
//        String name = user.getDisplayName();
//        String email = user.getEmail();
//
//        // Check if user's email is verified
//        boolean emailVerified = user.isEmailVerified();
//
//        // The user's ID, unique to the Firebase project. Do NOT use this value to
//        // authenticate with your backend server, if you have one. Use
//        // FirebaseUser.getIdToken() instead.
//        String uid = user.getUid();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mconfirmpassword = findViewById(R.id.confirmpassword);
        mPhone = findViewById(R.id.phone);
        mRegister = findViewById(R.id.register);
        mloginText = findViewById(R.id.loginText);

        fAuth = FirebaseAuth.getInstance();
        mprogressBar = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = mFullName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmpassword = mconfirmpassword.getText().toString().trim();


                if (TextUtils.isEmpty(fullname)) {

                    mFullName.setError("Full Name is Require");


                } else if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Email is Require");

                } else if (TextUtils.isEmpty(phone)) {

                    mPhone.setError("Phone Number is Require");

                } else if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Password is Require");

                } else if (password.length() < 6) {

                    mPassword.setError("Password require 6 digits");

                } else if (!password.equals(confirmpassword)) {

                    mconfirmpassword.setError("Password Not matching");

                } else {

                mprogressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(Register.this, "Invalid Email Address",Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(View.GONE);

                        } else if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });

            }

            }

        });

        mloginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }

        });

        }
 }