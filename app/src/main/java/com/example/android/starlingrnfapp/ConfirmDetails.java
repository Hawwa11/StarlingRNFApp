package com.example.android.starlingrnfapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ConfirmDetails extends AppCompatActivity {
Button confirm;
EditText name, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);

        confirm = findViewById(R.id.btnconfirm);
        name = findViewById(R.id.cname);
        email = findViewById(R.id.cemail);
        phone = findViewById(R.id.cphno);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");
        final String pax = bundle.getString("nopax");
        final String time = bundle.getString("time");

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference docref1 = fStore.collection("users").document(userID);
        docref1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("fname"));
                email.setText(value.getString("email"));
                phone.setText(value.getString("phone"));
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Phoneno = phone.getText().toString().trim();
                userID = fAuth.getCurrentUser().getUid();

                DocumentReference documenentReference = fStore.collection("reservations").document(userID);
                Map<String, Object> preserve = new HashMap<>();
                preserve.put("name", Name);
                preserve.put("phone", Phoneno);
                preserve.put("email", Email);
                preserve.put("time", time);
                preserve.put("date", date);
                preserve.put("no_pax", pax);
                documenentReference.set(preserve);
                Toast.makeText(ConfirmDetails.this, "Parking Reserved Succesfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });
    }
}