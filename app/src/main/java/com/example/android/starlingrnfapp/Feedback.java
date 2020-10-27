package com.example.android.starlingrnfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class Feedback extends AppCompatActivity {
RatingBar service, food, hygiene;
Button submit;
EditText review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        service = findViewById(R.id.rtservice);
        food = findViewById(R.id.rtfood);
        hygiene = findViewById(R.id.rthygiene);
        submit = findViewById(R.id.submitfeedback);
        review = findViewById(R.id.reviewbox);

    }
}