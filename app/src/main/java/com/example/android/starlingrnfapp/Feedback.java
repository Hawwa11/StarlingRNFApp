package com.example.android.starlingrnfapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Feedback extends AppCompatActivity {
RatingBar service, food, hygiene;
Button submit;
EditText review;
FirebaseFirestore fstore;
FirebaseAuth fAuth;
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_feedback);

        service = findViewById(R.id.rtservice);
        food = findViewById(R.id.rtfood);
        hygiene = findViewById(R.id.rthygiene);
        submit = findViewById(R.id.submitfeedback);
        review = findViewById(R.id.reviewbox);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        DocumentReference documenentReference = fstore.collection("feedback").document(userID);
        documenentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                review.setText(value.getString("review"));
                if( review.getText().toString().isEmpty() != true) {


                    String RService = value.getString("rate_service");
                    String Rhygiene = value.getString("rate_hygiene");
                    String RFood = value.getString("rate_food_quality");


                    float rates = Float.parseFloat(RService);
                    float rateh = Float.parseFloat(Rhygiene);
                    float ratef = Float.parseFloat(RFood);

                    service.setRating(rates);
                    food.setRating(rateh);
                    hygiene.setRating(ratef);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Review = review.getText().toString().trim();
                String Service = String.valueOf(service.getRating());
                String Hygiene = String.valueOf(hygiene.getRating());
                String Food = String.valueOf(food.getRating());
                if(TextUtils.isEmpty(Review)){
                    review.setError("No review entered");
                    return;
                }
                final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                DocumentReference documenentReference = fstore.collection("feedback").document(userID);
                Map<String, Object> preserve = new HashMap<>();
                preserve.put("rate_service", Service);
                preserve.put("rate_hygiene", Hygiene);
                preserve.put("rate_food_quality", Food);
                preserve.put("review", Review);
                preserve.put("date", date);
                documenentReference.set(preserve);
                Toast.makeText(Feedback.this, "Feedback Submitted Succesfully.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}