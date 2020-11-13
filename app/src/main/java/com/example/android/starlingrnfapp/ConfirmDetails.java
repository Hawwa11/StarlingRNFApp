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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ConfirmDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
Button confirm;
EditText name, email, phone;
Button back;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String resID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_confirm_details);

        confirm = findViewById(R.id.btnconfirm);
        back = findViewById(R.id.back2_reserveform);
        name = findViewById(R.id.cname);
        email = findViewById(R.id.cemail);
        phone = findViewById(R.id.cphno);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ReserveForm.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        final String Date = bundle.getString("date");
        final String pax = bundle.getString("nopax");
        final String time = bundle.getString("time");
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
                if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                    Toast.makeText(ConfirmDetails.this, "One or many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Name = name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Phoneno = phone.getText().toString().trim();
                userID = fAuth.getCurrentUser().getUid();
                resID = "reservation" + getRandomString(10);
                DocumentReference documenentReference = fStore.collection("reservations").document(userID);
                Map<String, Object> preserve = new HashMap<>();
                preserve.put("ID", resID);
                preserve.put("name", Name);
                preserve.put("phone", Phoneno);
                preserve.put("email", Email);
                preserve.put("time", time);
                preserve.put("rev_date", Date);
                preserve.put("no_pax", pax);
                preserve.put("date", date);
                documenentReference.set(preserve);


                DocumentReference dr = fStore.collection("reservations").document(userID).collection("history").document(resID);
                Map<String, Object> save = new HashMap<>();
                save.put("ID", resID);
                save.put("name", Name);
                save.put("phone", Phoneno);
                save.put("email", Email);
                save.put("time", time);
                save.put("rev_date", Date);
                save.put("no_pax", pax);
                save.put("date", date);
                dr.set(save);
                Toast.makeText(ConfirmDetails.this, "Reservation Booked Succesfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
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