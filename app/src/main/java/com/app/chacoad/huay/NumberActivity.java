package com.app.chacoad.huay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.chacoad.huay.Model.Customer;
import com.app.chacoad.huay.Model.LotoNumber;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class NumberActivity extends AppCompatActivity implements View.OnClickListener {
    int numberCount = 1;
    int priceCount = 1;
    String keyCustomerName;
    String keyCustomerId;
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
                keyCustomerName = null;
                keyCustomerId = null;
            } else {
                keyHuayDate = extras.getString("key_huay_date");
                keyCustomerName = extras.getString("key_customer_name");
                keyCustomerId = extras.getString("key_customer_id");
            }
        } else {
            keyHuayDate = (String) savedInstanceState.getSerializable("key_huay_date");
            keyCustomerName = (String) savedInstanceState.getSerializable("key_customer_name");
            keyCustomerId = (String) savedInstanceState.getSerializable("key_customer_id");
        }
        setTitle(keyCustomerName);
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
                        insertDatabase(Long.parseLong(number), Long.parseLong(price));
                    } else {
                        Toast.makeText(this, "ใส่จำนวนเงิน 1 ถึง 4 ตัว", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ใส่ตัวเลข 2 หรือ 3 ตัว", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void insertDatabase(long number, long price) {
        Customer cus = new Customer();
        cus.setCustomerName(keyCustomerName);
        cus.setCustomerId(Long.parseLong(keyCustomerId));
        LotoNumber num = new LotoNumber(number, price);
        HashMap<String, LotoNumber> data = new HashMap<String, LotoNumber>();
        data.put("n1", num);
        cus.setNumbers(data);

        mDatabase.child(keyHuayDate).child("c" + keyCustomerId).setValue(cus);
//        mDatabase.child(keyHuayDate).child(keyCustomerId).child("number_" + numberCount++).setValue(text);
//        mDatabase.child(keyHuayDate).child(keyCustomerId).child("price_" + priceCount++).setValue(price);
    }
}
