package com.app.chacoad.huay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.chacoad.huay.Util.DayDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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


        mainActivityButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_button1:
                Intent intent = new Intent(this, NumberActivity.class);
                startActivity(intent);
                break;
        }

    }
}
