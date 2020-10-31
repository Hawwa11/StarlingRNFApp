package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Set;

public class EditProfile extends AppCompatActivity {
    public static final String TAG = "EditProfile";
    Button back,save;
    EditText name,email,phone;
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
        setContentView(R.layout.activity_edit_profile);
        back = findViewById(R.id.btnpback);
        save= findViewById(R.id.btnpsave);
        name = findViewById(R.id.epname);
        email = findViewById(R.id.epemail);
        phone = findViewById(R.id.epphoneno);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();



        DocumentReference documenentReference = fStore.collection("users").document(userID);
        documenentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("fname"));
                email.setText(value.getString("email"));
                phone.setText(value.getString("phone"));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "One or amny fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }


                String Email = email.getText().toString().trim();
                String Name = name.getText().toString().trim();;
                String Phone= phone.getText().toString().trim();;
                final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());



                user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                DocumentReference defref = fStore.collection("users").document(userID);
                Map<String, Object> edited = new HashMap<>();
                edited.put("fname",Name);
                edited.put("email",Email);
                edited.put("phone",Phone);
                edited.put("edit_date",date);
                defref.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess: profile details updated for for "+ userID);
                    }
                });
                Toast.makeText(EditProfile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
//
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });
    }
}