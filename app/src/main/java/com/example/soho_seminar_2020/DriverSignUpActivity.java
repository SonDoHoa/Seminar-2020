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

import com.example.soho_seminar_2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverSignUpActivity extends AppCompatActivity {

    private TextView tvToSignIn;
    private Button btnSignUp;
    private EditText s1_email, s2_password, s3_username;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);
        getSupportActionBar().show();
        tvToSignIn = (TextView)findViewById(R.id.toSignInDrivers);
        s1_email=(EditText)findViewById(R.id.mailDrivers);
        s2_password=(EditText)findViewById(R.id.passDrivers);
        s3_username=(EditText)findViewById(R.id.nameDrivers);
        btnSignUp=(Button)findViewById(R.id.signUpDrivers);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        tvToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverSignUpActivity.this,DriverSignInActivity.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    protected  void signUpUser(){
        dialog.setMessage("Registering. Please wait.");
        dialog.show();

        if(s3_username.getText().toString().equals("") || s1_email.getText().toString().equals("") || s2_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(s1_email.getText().toString(),s2_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                String userId = mAuth.getCurrentUser().getUid();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(userId)
                                        .child("name");
                                reference.setValue(s1_email.getText().toString());
                                finish();
                            } else{
                                dialog.hide();
                                Toast.makeText(getApplicationContext(),"Authentication failed.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
        }
    }

    protected void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed up successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(DriverSignUpActivity.this,MapDriverActivity.class));
        }else {
            Toast.makeText(this,"You didn't signed up",Toast.LENGTH_LONG).show();
        }
    }
}
