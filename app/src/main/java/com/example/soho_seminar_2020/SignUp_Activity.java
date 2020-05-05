package com.example.soho_seminar_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Activity extends AppCompatActivity {
    private TextView tvToSignIn;
    private EditText s1_email, s2_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);
        tvToSignIn = (TextView)findViewById(R.id.tvToSignIn);
        s1_email=(EditText)findViewById(R.id.txtEmail);
        s2_password=(EditText)findViewById(R.id.txtPass);
        mAuth = FirebaseAuth.getInstance();

        tvToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp_Activity.this,SignIn_Activity.class);
                startActivity(i);
            }
        });
    }

    protected  void signUpUser(View v){
        if(s1_email.getText().toString().equals("") || s2_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(s1_email.getText().toString(),s2_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"create with email: success",Toast.LENGTH_SHORT).show();
                                FirebaseUser createUser = mAuth.getCurrentUser();
                                updateUI(createUser);
                            } else{
                                Toast.makeText(getApplicationContext(),"Authentication failed.",Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    protected void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed up successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,SignIn_Activity.class));
        }else {
            Toast.makeText(this,"You didn't signed up",Toast.LENGTH_LONG).show();
        }
    }
}
