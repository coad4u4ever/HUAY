package com.app.chacoad.huay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chacoad.huay.Adapter.HuayAdapter;
import com.app.chacoad.huay.Util.DayDate;
import com.app.chacoad.huay.Util.Lotto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView mainActivityDate = null;
    ListView mainActivityListView = null;
    DayDate dayDate;
    ArrayList<String[]> huayArrayList;
    HuayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityDate = findViewById(R.id.main_activity_date);
        mainActivityListView = findViewById(R.id.main_activity_listview);
        dayDate = new DayDate(this);
        String dateFormat = getString(R.string.th_day_today) + " " + dayDate.getDateFullFormat();
        String nextHuayDate = getString(R.string.th_day_huay_tx_1) + " " + dayDate.getNextHuayDate();
        mainActivityDate.setText(dateFormat);
        Lotto lotto = new Lotto();
        lotto.start();
        huayArrayList = new ArrayList<>();


        String[] value = {dayDate.getNextHuayDateKey(), nextHuayDate};

        huayArrayList.add(value);

        adapter = new HuayAdapter(this, huayArrayList);

        mainActivityListView.setAdapter(adapter);

        mainActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] item = (String[]) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                intent.putExtra("key_huay_date", item[0]);
                startActivity(intent);
            }
        });

    }
}
