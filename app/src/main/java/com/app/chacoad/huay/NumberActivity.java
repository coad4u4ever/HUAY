package com.app.chacoad.huay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NumberActivity extends AppCompatActivity implements View.OnClickListener {
    int numberCount = 1;
    int priceCount = 1;
    String keyHuayDate;
    private Button numberActivityAdd;
    private EditText numberActivityNumber;
    private EditText numberActivityPrice;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        numberActivityNumber = findViewById(R.id.number_activity_number);
        numberActivityAdd = findViewById(R.id.number_activity_add);
        numberActivityPrice = findViewById(R.id.number_activity_price);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                keyHuayDate = null;
            } else {
                keyHuayDate = extras.getString("key_huay_date");
            }
        } else {
            keyHuayDate = (String) savedInstanceState.getSerializable("key_huay_date");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        numberActivityAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.number_activity_add:
                String number = numberActivityNumber.getText().toString();
                String price = numberActivityPrice.getText().toString();
                if (number.length() == 2 || number.length() == 3) {
                    if (price.length() >= 1 && price.length() <= 4) {
                        insertDatabase(Integer.parseInt(number), Integer.parseInt(price));
                    } else {
                        Toast.makeText(this, "ใส่จำนวนเงิน 1 ถึง 4 ตัว", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ใส่ตัวเลข 2 หรือ 3 ตัว", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void insertDatabase(int text, int price) {
        mDatabase.child(keyHuayDate).child("customer1").child("number_" + numberCount++).setValue(text);
        mDatabase.child(keyHuayDate).child("customer1").child("price_" + priceCount++).setValue(price);
    }
}
