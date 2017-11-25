package com.app.chacoad.huay.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class LotoNumber {
    private String keyname;
    private Long number;
    private Long price;

    public LotoNumber() {
    }

    public LotoNumber(Long number, Long price) {
        this.number = number;
        this.price = price;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }
}
