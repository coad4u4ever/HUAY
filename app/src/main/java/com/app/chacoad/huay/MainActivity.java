package com.app.chacoad.huay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.chacoad.huay.Util.DayDate;
import com.app.chacoad.huay.Util.Lotto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mainActivityDate = null;
    Button mainActivityButton1 = null;
    DayDate dayDate;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityDate = findViewById(R.id.main_activity_date);
        mainActivityButton1 = findViewById(R.id.main_activity_button1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dayDate = new DayDate(this);
        String dateFormat = getString(R.string.th_day_today) + " " + dayDate.getDateFullFormat();
        String nextHuayDate = getString(R.string.th_day_huay_tx_1) + " " + dayDate.getNextHuayDate();
        mainActivityDate.setText(dateFormat);
        mainActivityButton1.setText(nextHuayDate);
        Lotto lotto = new Lotto();
        lotto.start();

        mainActivityButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_button1:
                Intent intent = new Intent(this, CustomerActivity.class);
                intent.putExtra("key_huay_date", dayDate.getNextHuayDateKey());
                startActivity(intent);
                break;
        }

    }
}
