package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StaffLogin extends AppCompatActivity {
    EditText email, password;
    TextView reset, customer;
    FirebaseAuth fAuth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        customer = findViewById(R.id.tvswitchtocus);
        login = findViewById(R.id.btnlogin2);
        email = findViewById(R.id.etemail2);
        password = findViewById(R.id.etpass2);
        fAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    password.setError("Password should be more than 6 characters.");
                    return;
                }

                final String manageremail = "manager@starling.com";

                //authentecate user
                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StaffLogin.this, "Logged in succesfully.", Toast.LENGTH_SHORT).show();
                            if (Email.equals(manageremail)) {
                                startActivity(new Intent(getApplicationContext(), ManagerDashboard.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), StaffDashboard.class));
                            }
                        } else {
                            Toast.makeText(StaffLogin.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        });



        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}

