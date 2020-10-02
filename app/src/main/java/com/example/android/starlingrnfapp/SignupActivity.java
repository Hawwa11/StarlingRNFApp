package com.example.android.starlingrnfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "Signup";

    EditText name,vehicleno,email,password,cpassword,phone,intiid;
    CheckBox agree;
    Button signup;
    TextView goLogin,title,fname,fusername,femail,fpassword,fcpassword,fvehicleno;
    Toolbar toolbar;
    FirebaseAuth fAuth;
    String userID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.etname);
        email =findViewById(R.id.etemail);
        password =findViewById(R.id.etpass);
        cpassword =findViewById(R.id.etcpass);
        phone =findViewById(R.id.etphoneno);
        agree =findViewById(R.id.cb_agree);
        signup = findViewById(R.id.btn_signup);
        goLogin=findViewById(R.id.btn_gologin);


        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                final String Name = name.getText().toString().trim();
                final String Phoneno=phone.getText().toString().trim();;

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required.");
                    return;
                }


                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is required.");
                    return;
                }

                if(password.length() < 6){
                    password.setError("Password must be more than 6 characters.");
                    return;
                }

//

                if(agree.isChecked()==false){
                    password.setError("Please agree to term and conditions.");
                    return;
                }

                //create user account
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Account Created.", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        } else{
                            Toast.makeText(SignupActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}