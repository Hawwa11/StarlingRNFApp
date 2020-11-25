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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Catering extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    EditText invoice,CAddress,CDate,CTime,Email,PNumber,BChicken,BBeef,CChicken, CBeef,CMMornay,SSBechamel,CConfit,CChop,CMeatball,MNCheese,MPotatoes,Pudding,ILTea,SDrinks,ARequest;
    TextView powr;
    Button mSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String caterID;
    TextWatcher tw = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
//Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{

                    int day = Integer.parseInt(clean.substring(0,2));
                    int mon = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));
                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<2020)?2020:(year>2030)?2030:year;
                    cal.set(Calendar.YEAR, year);

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                CDate.setText(current);
                CDate.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_catering);
        invoice = findViewById(R.id.pln_invoice);
        CAddress = findViewById(R.id.pln_CA);
        CDate = findViewById(R.id.pln_CD);
        CDate.addTextChangedListener(tw);
        CTime = findViewById(R.id.pln_CT);
        Email = findViewById(R.id.pln_Email);
        PNumber = findViewById(R.id.pln_PN);
        BChicken = findViewById(R.id.pln_BC);
        BBeef = findViewById(R.id.pln_BB);
        CChicken = findViewById(R.id.pln_CC);
        CBeef = findViewById(R.id.pln_CB);
        CMMornay = findViewById(R.id.pln_CMM);
        SSBechamel = findViewById(R.id.pln_SSB);
        CConfit = findViewById(R.id.pln_CConfit);
        CChop = findViewById(R.id.pln_CChop);
        CMeatball = findViewById(R.id.pln_CM);
        MNCheese = findViewById(R.id.pln_MNC);
        MPotatoes = findViewById(R.id.pln_MP);
        Pudding = findViewById(R.id.pln_P);
        ILTea = findViewById(R.id.pln_ILT);
        SDrinks = findViewById(R.id.pln_SD);
        ARequest = findViewById(R.id.pln_AR);

        mSubmit = findViewById(R.id.btn_submit);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        fAuth = FirebaseAuth.getInstance();
        caterID = "CateringID" + getRandomString(9);

        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Bundle bundle = getIntent().getExtras();
        DocumentReference docref1 = fStore.collection("users").document(userID);
        docref1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                invoice.setText(value.getString("fname"));
                Email.setText(value.getString("email"));
                PNumber.setText(value.getString("phone"));

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invoices = invoice.getText().toString().trim();
                String C_A = CAddress.getText().toString().trim();
                String C_D = CDate.getText().toString().trim();
                String C_T = CTime.getText().toString().trim();
                String E_mail = Email.getText().toString().trim();
                String Pnum = PNumber.getText().toString().trim();
                String BC = BChicken.getText().toString().trim();
                String BB = BBeef.getText().toString().trim();
                String CC = CChicken.getText().toString().trim();
                String CB = CBeef.getText().toString().trim();
                String CMM = CMMornay.getText().toString().trim();
                String SSB = SSBechamel.getText().toString().trim();
                String CCon = CConfit.getText().toString().trim();
                String Cch = CChop.getText().toString().trim();
                String CM = CMeatball.getText().toString().trim();
                String MNC = MNCheese.getText().toString().trim();
                String MP = MPotatoes.getText().toString().trim();
                String Pdg = Pudding.getText().toString().trim();
                String ILT = ILTea.getText().toString().trim();
                String SD = SDrinks.getText().toString().trim();
                String AR = ARequest.getText().toString().trim();
                userID = fAuth.getCurrentUser().getUid();

                if (TextUtils.isEmpty(invoices)) {
                    invoice.setError("Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(C_A)) {
                    CAddress.setError("Address is required.");
                    return;
                }

                if (TextUtils.isEmpty(C_D)) {
                    CDate.setError("Date is required.");
                    return;
                }
                if (TextUtils.isEmpty(C_T)) {
                    CTime.setError("Time is required");
                    return;
                }
                if (TextUtils.isEmpty(E_mail)) {
                    Email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Pnum)) {
                    PNumber.setError("Phone number is required");
                    return;
                }

                DocumentReference documentReference = fStore.collection("Catering").document(userID);
                Map<String, Object> preserve = new HashMap<>();
                preserve.put("A-Cater ID", caterID);
                preserve.put("B-Invoice/Billing Name", invoices);
                preserve.put("C-Catering Address", C_A);
                preserve.put("D-Catering Date", C_D);
                preserve.put("E-Catering Time", C_T);
                preserve.put("F-Email", E_mail);
                preserve.put("G-Phone Number", Pnum);
                preserve.put("H-Bolognaise-Chicken", BC);
                preserve.put("I-Bolognaise-Beef", BB);
                preserve.put("J-Carbonara-Chicken", CC);
                preserve.put("K-Carbonara-Beef", CB);
                preserve.put("L-Chicken Mushroom Mornay", CMM);
                preserve.put("M-Sausage Spinach Bechamel", SSB);
                preserve.put("N-Chicken Confit", CCon);
                preserve.put("O-Chicken Chop", Cch);
                preserve.put("P-Chicken Meatball", CM);
                preserve.put("Q-Mac N Cheese", MNC);
                preserve.put("R-Mashed Potatoes", MP);
                preserve.put("S-Pudding", Pdg);
                preserve.put("T-Ice Lemon Tea", ILT);
                preserve.put("U-Soft Drinks", SD);
                preserve.put("V-Additional Request", AR);
                preserve.put("W-Order Status", "");

                documentReference.set(preserve);

                DocumentReference dr = fStore.collection("Catering").document(userID).collection("Catering History").document(caterID);
                Map<String, Object> save = new HashMap<>();
                save.put("A-Cater ID", caterID);
                save.put("B-Invoice/Billing Name", invoices);
                save.put("C-Catering Address", C_A);
                save.put("D-Catering Date", C_D);
                save.put("E-Catering Time", C_T);
                save.put("F-Email", E_mail);
                save.put("G-Phone Number", Pnum);
                save.put("H-Bolognaise-Chicken", BC);
                save.put("I-Bolognaise-Beef", BB);
                save.put("J-Carbonara-Chicken", CC);
                save.put("K-Carbonara-Beef", CB);
                save.put("L-Chicken Mushroom Mornay", CMM);
                save.put("M-Sausage Spinach Bechamel", SSB);
                save.put("N-Chicken Confit", CCon);
                save.put("O-Chicken Chop", Cch);
                save.put("P-Chicken Meatball", CM);
                save.put("Q-Mac N Cheese", MNC);
                save.put("R-Mashed Potatoes", MP);
                save.put("S-Pudding", Pdg);
                save.put("T-Ice Lemon Tea", ILT);
                save.put("U-Soft Drinks", SD);
                save.put("V-Additional Request", AR);

                dr.set(save);
                Toast.makeText(Catering.this, "Booking Done Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Catering.class));

            }
        });
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
}


