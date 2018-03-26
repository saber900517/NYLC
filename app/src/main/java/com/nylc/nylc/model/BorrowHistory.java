package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/3/26.
 */

public class BorrowHistory implements Parcelable {
    private int state;
    private String money;
    private String date;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
        dest.writeString(this.money);
        dest.writeString(this.date);
    }

    public BorrowHistory() {
    }

    protected BorrowHistory(Parcel in) {
        this.state = in.readInt();
        this.money = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<BorrowHistory> CREATOR = new Parcelable.Creator<BorrowHistory>() {
        @Override
        public BorrowHistory createFromParcel(Parcel source) {
            return new BorrowHistory(source);
        }

        @Override
        public BorrowHistory[] newArray(int size) {
            return new BorrowHistory[size];
        }
    };
}
