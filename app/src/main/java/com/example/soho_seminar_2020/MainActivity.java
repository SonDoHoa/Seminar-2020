package com.example.soho_seminar_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView buttonDrivers, buttonCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        buttonDrivers = (TextView)findViewById(R.id.tvDriver);
        buttonCustomers = (TextView)findViewById(R.id.tvCustomer);

        buttonDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DriverSignInActivity.class);
                startActivity(i);
            }
        });

        buttonCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CustomerSignInActivity.class);
                startActivity(i);
            }
        });
    }
}
