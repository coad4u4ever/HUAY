package com.app.chacoad.huay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.app.chacoad.huay.Util.DayDate;

public class MainActivity extends AppCompatActivity {
    TextView mainActivityDate = null;
    Button mainActivityButton1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityDate = findViewById(R.id.main_activity_date);
        mainActivityButton1 = findViewById(R.id.main_activity_button1);
        DayDate dayDate = new DayDate(this);
        String dateFormat = getString(R.string.th_day_today) + " " + dayDate.getDateFullFormat();
        String nextHuayDate = getString(R.string.th_day_huay_tx_1) + " " + dayDate.getNextHuayDate();
        mainActivityDate.setText(dateFormat);
        mainActivityButton1.setText(nextHuayDate);
    }
}
