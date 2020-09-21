package rob.sample.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone, mconfirmpassword;
    TextView mloginText;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar mprogressBar;

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
                String email = mEmail.getText().toString().trim();
                String password =mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Email is Require");
                    return;
                } else if(!password.equals(mconfirmpassword)){
                    Toast.makeText(Register.this,"Password Not matching",Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Email is Password");
                    return;

                }

                if (password.length() <6) {
                    mPassword.setError("Password require 6 digits");
                    return;
                }

                mprogressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {

                            Toast.makeText(Register.this, "Error! ", Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(View.GONE);

                        }
                    }
                });
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