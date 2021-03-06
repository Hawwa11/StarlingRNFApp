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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

public class EditReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static final String TAG ="EditReservation";
Button back,save;
EditText name,email,phone,date,time,pax;
FirebaseFirestore fStore;
FirebaseAuth fAuth;
String userID;
FirebaseUser user;
    String resID;

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
                resID = value.getString("ID");

            }
        });

//
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                        time.getText().toString().isEmpty() || date.getText().toString().isEmpty() || pax.getText().toString().isEmpty()) {
                    Toast.makeText(EditReservation.this, "One or many fields are empty.", Toast.LENGTH_SHORT).show();
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
                edited.put("ID", resID);
                edited.put("name",Name);
                edited.put("no_pax",Pax);
                edited.put("email",Email);
                edited.put("time",Time);
                edited.put("phone",Phone);
                edited.put("rev_date",Date);
                edited.put("edit_date",date);
                edited.put("status","");
                defref.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess: reservation details updated for for "+ userID);
                    }
                });
                DocumentReference dr = fStore.collection("reservations").document(userID).collection("history").document(resID);
                Map<String, Object> save = new HashMap<>();
                save.put("ID", resID);
                save.put("name", Name);
                save.put("phone", Phone);
                save.put("email", Email);
                save.put("time", Time);
                save.put("rev_date", Date);
                save.put("no_pax", Pax);
                save.put("edit_date", date);
                save.put("status","");
                dr.set(save);
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
            case R.id.nav_catering:
                startActivity(new Intent(getApplicationContext(),Catering.class));
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