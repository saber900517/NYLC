package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 我的预定
 * Created by kasim on 2018/3/27.
 */

public class MyOrder implements Parcelable {
    private String name;
    private String price;
    private String count;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public MyOrder() {
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
    }

    protected MyOrder(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.count = in.readString();
        this.state = in.readInt();
    }

    public static final Creator<MyOrder> CREATOR = new Creator<MyOrder>() {
        @Override
        public MyOrder createFromParcel(Parcel source) {
            return new MyOrder(source);
        }

        @Override
        public MyOrder[] newArray(int size) {
            return new MyOrder[size];
        }
    };
}
