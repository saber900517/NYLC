package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/3/27.
 */

public class MySale implements Parcelable {
    private String name;
    private String price;
    private String count;
    private int state;
    private String earnest;

    public String getEarnest() {
        return earnest;
    }

    public void setEarnest(String earnest) {
        this.earnest = earnest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MySale() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.count);
        dest.writeInt(this.state);
        dest.writeString(this.earnest);
    }

    protected MySale(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.count = in.readString();
        this.state = in.readInt();
        this.earnest = in.readString();
    }

    public static final Creator<MySale> CREATOR = new Creator<MySale>() {
        @Override
        public MySale createFromParcel(Parcel source) {
            return new MySale(source);
        }

        @Override
        public MySale[] newArray(int size) {
            return new MySale[size];
        }
    };
}
