package com.example.soho_seminar_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class CustomerSignInActivity extends AppCompatActivity {

    private TextView tvToSignUp;
    private Button btnSignIn;
    private EditText s4_email,s5_password;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in);
        getSupportActionBar().hide();
        tvToSignUp = (TextView)findViewById(R.id.btnToSignUpCus);
        btnSignIn=(Button)findViewById(R.id.btnSignInCus);
        s4_email=(EditText)findViewById(R.id.editTextMailCustomers);
        s5_password=(EditText)findViewById(R.id.editTextPassCustomers);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null){
                    startActivity(new Intent(CustomerSignInActivity.this,MapDriverActivity.class));
                    finish();
                    return;
                }
            }
        };

        dialog=new ProgressDialog(this);

        tvToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerSignInActivity.this, CustomerSingUpActivity.class);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });
    }

    protected void signInUser(){
        dialog.setMessage("Signing in. Please wait.");
        dialog.show();

        if(s4_email.getText().toString().equals("") || s5_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(s4_email.getText().toString(),s5_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                finish();
                            } else {
                                dialog.hide();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });
        }
    }

    protected void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed in successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MapCustomerActivity.class));
        }else {
            Toast.makeText(this,"You didn't signed in",Toast.LENGTH_LONG).show();
        }
    }
}
