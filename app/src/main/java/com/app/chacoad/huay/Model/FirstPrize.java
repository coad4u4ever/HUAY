package com.app.chacoad.huay.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Non on 11/16/2017.
 */

@IgnoreExtraProperties
public class FirstPrize {
    private Long number;

    public FirstPrize() {
    }

    public FirstPrize(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
