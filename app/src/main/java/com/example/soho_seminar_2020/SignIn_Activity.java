package com.example.soho_seminar_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignIn_Activity extends AppCompatActivity {
    private TextView tvToSignUp;
    private Button btnSignIn;
    private EditText s3_email,s4_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);
        tvToSignUp = (TextView)findViewById(R.id.tvToSignUp);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        s3_email=(EditText)findViewById(R.id.txtUserId);
        s4_password=(EditText)findViewById(R.id.txtPassId);
        mAuth = FirebaseAuth.getInstance();

        tvToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(this);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    protected void signInUser(View.OnClickListener v){
        if(s3_email.getText().toString().equals("") || s4_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(s3_email.getText().toString(),s4_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });
        }
    }

    protected void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed in successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,SignIn_Activity.class));
        }else {
            Toast.makeText(this,"You didn't signed in",Toast.LENGTH_LONG).show();
        }
    }
}
