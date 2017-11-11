package com.app.chacoad.huay.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Customer {
    private Long customerId;
    private String customerName;
    private HashMap<String, LotoNumber> numbers;

    public Customer() {
    }

    public Customer(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, LotoNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(HashMap<String, LotoNumber> numbers) {
        this.numbers = numbers;
    }
}
