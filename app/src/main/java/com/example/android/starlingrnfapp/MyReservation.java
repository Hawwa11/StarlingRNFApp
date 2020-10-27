package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyReservation extends AppCompatActivity {
    public static final String TAG = "MyReservation";
    private DrawerLayout drawer;
    TextView name,phone,email ,paxno ,timeslot,empty,date;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button edit, back, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);



        name =findViewById(R.id.rname);
        email=findViewById(R.id.remail);
        paxno =findViewById(R.id.rpax);
        timeslot=findViewById(R.id.rtime);
        phone=findViewById(R.id.rphone);
        back = findViewById(R.id.btnback);
        edit=findViewById(R.id.btnedit);
        empty=findViewById(R.id.empty);
        date=findViewById(R.id.rdate);
        delete = findViewById(R.id.btndelete);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();




        DocumentReference documenentReference = fStore.collection("reservations").document(userID);
        documenentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("name"));
                email.setText(value.getString("email"));
                paxno.setText(value.getString("no_pax"));
                phone.setText(value.getString("phone"));
                timeslot.setText(value.getString("time"));
                date.setText(value.getString("date"));
                if( name.getText().toString().isEmpty() != true) {
                    empty.setText(" ");
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditReservation.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = fAuth.getCurrentUser().getUid();
                fStore.collection("reservations").document(userID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                empty.setText("No Reservation Made Yet!");

            }
        });
    }
}