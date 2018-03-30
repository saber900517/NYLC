package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/3/29.
 */

public class MeansOfProduction implements Parcelable {
    private String name;
    private String town;
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.town);
        dest.writeString(this.count);
    }

    public MeansOfProduction() {
    }

    protected MeansOfProduction(Parcel in) {
        this.name = in.readString();
        this.town = in.readString();
        this.count = in.readString();
    }

    public static final Parcelable.Creator<MeansOfProduction> CREATOR = new Parcelable.Creator<MeansOfProduction>() {
        @Override
        public MeansOfProduction createFromParcel(Parcel source) {
            return new MeansOfProduction(source);
        }

        @Override
        public MeansOfProduction[] newArray(int size) {
            return new MeansOfProduction[size];
        }
    };
}
