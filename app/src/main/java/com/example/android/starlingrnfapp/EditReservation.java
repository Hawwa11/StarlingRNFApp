package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class EditReservation extends AppCompatActivity {
    public static final String TAG ="EditReservation";
Button back,save;
EditText name,email,phone,date,time,pax;
FirebaseFirestore fStore;
FirebaseAuth fAuth;
String userID;
FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_edit_reservation);
        back = findViewById(R.id.btnerback);
        save= findViewById(R.id.btnersave);
        name = findViewById(R.id.ername);
        email = findViewById(R.id.eremail);
        phone = findViewById(R.id.erphoneno);
        date=findViewById(R.id.erdate);
        time=findViewById(R.id.ertime);
        pax=findViewById(R.id.erpax);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documenentReference = fStore.collection("reservations").document(userID);
        documenentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("name"));
                email.setText(value.getString("email"));
                pax.setText(value.getString("no_pax"));
                phone.setText(value.getString("phone"));
                time.setText(value.getString("time"));
                date.setText(value.getString("rev_date"));

            }
        });

//
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                        time.getText().toString().isEmpty() || date.getText().toString().isEmpty() || pax.getText().toString().isEmpty()) {
                    Toast.makeText(EditReservation.this, "One or amny fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }


                String Email = email.getText().toString().trim();
                String Name = name.getText().toString().trim();;
                String Phone= phone.getText().toString().trim();;
                String Time=time.getText().toString().trim();;
                String Date=date.getText().toString().trim();;
                String Pax=pax.getText().toString().trim();;
                final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                DocumentReference defref = fStore.collection("reservations").document(userID);
                Map<String, Object> edited = new HashMap<>();
                edited.put("name",Name);
                edited.put("no_pax",Pax);
                edited.put("email",Email);
                edited.put("time",Time);
                edited.put("phone",Phone);
                edited.put("rev_date",Date);
                edited.put("edit_date",date);
                defref.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess: reservation details updated for for "+ userID);
                    }
                });
                Toast.makeText(EditReservation.this, "Reservation Details Updated!", Toast.LENGTH_SHORT).show();
//
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MyReservation.class));
            }
        });
    }

}