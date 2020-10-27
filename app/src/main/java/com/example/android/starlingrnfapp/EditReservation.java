package com.example.android.starlingrnfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditReservation extends AppCompatActivity {
Button back,save;
EditText name,email,phoneno,date,time,pax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);
        back = findViewById(R.id.btnerback);
        save= findViewById(R.id.btnersave);
        name = findViewById(R.id.ername);
        email = findViewById(R.id.eremail);
        phoneno = findViewById(R.id.erphoneno);
        date=findViewById(R.id.erdate);
        time=findViewById(R.id.ertime);
        pax=findViewById(R.id.erpax);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MyReservation.class));
            }
        });
    }
}