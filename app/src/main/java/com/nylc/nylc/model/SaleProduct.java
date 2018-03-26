package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/3/26.
 */

public class SaleProduct implements Parcelable {
    private String name;
    private String beforeYesterdayPrice;
    private String yesterdayPrice;
    private String todayPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeforeYesterdayPrice() {
        return beforeYesterdayPrice;
    }

    public void setBeforeYesterdayPrice(String beforeYesterdayPrice) {
        this.beforeYesterdayPrice = beforeYesterdayPrice;
    }

    public String getYesterdayPrice() {
        return yesterdayPrice;
    }

    public void setYesterdayPrice(String yesterdayPrice) {
        this.yesterdayPrice = yesterdayPrice;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.beforeYesterdayPrice);
        dest.writeString(this.yesterdayPrice);
        dest.writeString(this.todayPrice);
    }

    public SaleProduct() {
    }

    protected SaleProduct(Parcel in) {
        this.name = in.readString();
        this.beforeYesterdayPrice = in.readString();
        this.yesterdayPrice = in.readString();
        this.todayPrice = in.readString();
    }

    public static final Parcelable.Creator<SaleProduct> CREATOR = new Parcelable.Creator<SaleProduct>() {
        @Override
        public SaleProduct createFromParcel(Parcel source) {
            return new SaleProduct(source);
        }

        @Override
        public SaleProduct[] newArray(int size) {
            return new SaleProduct[size];
        }
    };
}
