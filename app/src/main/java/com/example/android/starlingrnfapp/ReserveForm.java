package com.example.android.starlingrnfapp;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_reserve_form);
         datePicker = findViewById(R.id.datePicker1);
         submit = findViewById(R.id.submit_button);
         nopax = findViewById(R.id.edit_pax);

        Bundle bundle = getIntent().getExtras();

        datePicker.init(
                datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                       selectedYear = year;
                       selectedMonth = monthOfYear;
                      selectedDay = dayOfMonth;
                      date = selectedDay + "/" + selectedMonth + "/" + selectedYear;
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
        time = "12PM - 1PM";

    }
    public void rb2(View view) {
        time = "1PM - 2PM";

    }
    public void rb3(View view) {
        time = "2PM - 3PM";

    }
    public void rb4(View view) {
        time = "6PM - 7PM";

    }
    public void rb5(View view) {
        time = "7PM - 8PM";

    }
    public void rb6(View view) {
        time = "8PM - 9PM";

    }



}