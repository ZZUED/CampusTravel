package com.zzued.campustravel.modelclass;

import java.util.Date;
import java.util.GregorianCalendar;

public class Coupon {
    private int cardId;
    private String storeName;
    private double storeDiscount;
    private long starttime;
    private long endtime;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getStoreDiscount() {
        return storeDiscount;
    }

    public void setStoreDiscount(double storeDiscount) {
        this.storeDiscount = storeDiscount;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
    public int getDeadline(){
        long dif = endtime - new GregorianCalendar().getTimeInMillis();
        return (int) (dif / 1000 / 3600 / 24);
    }
}
