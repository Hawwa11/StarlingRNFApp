package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ReserveForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
DatePicker datePicker;
Button submit;
EditText nopax;
String time;
RadioGroup rg1, rg2, rb3, rb4, rb5, rb6;
int selectedYear , selectedMonth, selectedDay;
String date;

public static final String TAG = "ReserverForm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_reserve_form);
         datePicker = findViewById(R.id.datePicker1);
         submit = findViewById(R.id.submit_button);
         nopax = findViewById(R.id.edit_pax);
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);


        Bundle bundle = getIntent().getExtras();

        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        datePicker.init(
                datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                       selectedYear = year;
                       selectedMonth = monthOfYear+1;
                      selectedDay = dayOfMonth;
                      date = selectedYear  + "-" + selectedMonth + "-" + selectedDay;
                        Log.d(TAG, "selectedDate = " + selectedYear  + "-" + selectedMonth + "-" + selectedDay );
                    }
                });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pax = nopax.getText().toString().trim();
                Intent intent = new Intent(ReserveForm.this, ConfirmDetails.class);
                intent.putExtra("date", date);
                intent.putExtra("nopax",pax);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void handleCombinedClick(View view) {

        rg1.clearCheck();
        rg2.clearCheck();

        ((RadioButton) view).setChecked(true);

        switch (view.getId()) {
            case R.id.rb1:
                time = "12PM";
                break;

            case R.id.rb2:
                time = "1PM";
                break;

            case R.id.rb3:
                time = "2PM";
                break;

            case R.id.rb4:
                time = "6PM";
                break;

            case R.id.rb5:
                time = "7PM";
                break;

            case R.id.rb6:
                time = "8PM";
                break;
        }
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