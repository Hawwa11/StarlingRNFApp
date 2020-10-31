package com.example.android.starlingrnfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

public class ReserveForm extends AppCompatActivity {
DatePicker datePicker;
Button submit;
EditText nopax;
String time;
RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
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
    }


    public void rb1(View view) {
        time = "12PM";

    }
    public void rb2(View view) {
        time = "1PM";

    }
    public void rb3(View view) {
        time = "2PM";

    }
    public void rb4(View view) {
        time = "6PM";

    }
    public void rb5(View view) {
        time = "7PM";

    }
    public void rb6(View view) {
        time = "8PM";

    }

    public void handleCombinedClick(View view) {
// Manually set the check in the newly clicked radio button:
        ((RadioButton) view).setChecked(true);

// Perform any action desired for the new selection:
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

}