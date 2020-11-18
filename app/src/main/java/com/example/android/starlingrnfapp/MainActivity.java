package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_main);


    drawer = findViewById(R.id.drawer_layout);
    recyclerView = findViewById(R.id.rc_menu);


    NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        recyclerView();

}

    private void recyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<Menu> recycler=new ArrayList<>();
        recycler.add(new Menu(R.drawable.bolognaise,"Bolognaise (Chicken / Beef)"));
        recycler.add(new Menu(R.drawable.carbonara,"Carbonara (Chicken / Beef)"));
        recycler.add(new Menu(R.drawable.mornay,"Mushroom Mornay"));
        recycler.add(new Menu(R.drawable.bechamel,"Sausage Spinach Bechamel "));
        recycler.add(new Menu(R.drawable.macncheese,"Mac N Cheese"));
        recycler.add(new Menu(R.drawable.chickenchop,"Chicken Chop"));
        recycler.add(new Menu(R.drawable.chickenconfit,"Chicken Confit"));
        recycler.add(new Menu(R.drawable.chickenmeatball,"Chicken Meatballs"));

        adapter=new Menu_Adapter(recycler);
        recyclerView.setAdapter(adapter);
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
