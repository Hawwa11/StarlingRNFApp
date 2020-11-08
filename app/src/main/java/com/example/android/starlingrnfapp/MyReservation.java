package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static final String TAG = "MyReservation";
    TextView name,phone,email ,paxno ,timeslot,empty,date;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button edit, back, delete;
    String resID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
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
                date.setText(value.getString("rev_date"));
                resID = value.getString("ID");
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
                if( name.getText().toString().isEmpty() != true) {
                    startActivity(new Intent(getApplicationContext(), EditReservation.class));
                }
                else {
                    Toast.makeText(MyReservation.this, "No reservations booked yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( name.getText().toString().isEmpty() != true) {


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
                    fStore.collection("reservations").document(userID).collection("history").document(resID)
                            .delete();
                    empty.setText("No Reservation Made Yet!");
                }
                else {
                    Toast.makeText(MyReservation.this, "No reservations booked yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_main:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.nav_reserveform:
                startActivity(new Intent(getApplicationContext(),ReserveForm.class));
                break;
            case R.id.nav_myreservation:
                startActivity(new Intent(getApplicationContext(),MyReservation.class));
                break;
            case R.id.nav_helpcenter:
                startActivity(new Intent(getApplicationContext(),HelpCenter.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(),Settings.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(getApplicationContext(),Feedback.class));
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logged Out.", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}