package com.choco.android.choco.models;

/**
 * Created by win_user on 05/03/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Bpi {

    @SerializedName("USD")
    @Expose
    private Currency uSD;
    @SerializedName("GBP")
    @Expose
    private Currency gBP;
    @SerializedName("EUR")
    @Expose
    private Currency eUR;

    public Double getYesterday() {
        return yesterday;
    }

    public void setYesterday(Double d) {
        this.yesterday=d;
    }

    @Expose
    private Double yesterday;

    public Currency getUSD() {
        return uSD;
    }

    public void setUSD(Currency uSD) {
        this.uSD = uSD;
    }

    public Currency getGBP() {
        return gBP;
    }

    public void setGBP(Currency gBP) {
        this.gBP = gBP;
    }

    public Currency getEUR() {
        return eUR;
    }

    public void setEUR(Currency eUR) {
        this.eUR = eUR;
    }

}
