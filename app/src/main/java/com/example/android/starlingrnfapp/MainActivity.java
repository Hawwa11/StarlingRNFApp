package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    drawer = findViewById(R.id.drawer_layout);

    NavigationView navigationView = findViewById(R.id.nav_view);
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
        case R.id.nav_reserveparking:
        startActivity(new Intent(getApplicationContext(),ReserveForm.class));
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
      public void setting(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
}
