package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/4/16.
 */

public class SellType implements Parcelable {

    /**
     * CODETABLE_ITEM_ID : SELL_TYPE_2316598470
     * DISPLAY_NAME_ZH : 预定
     */

    private String CODETABLE_ITEM_ID;
    private String DISPLAY_NAME_ZH;

    public String getCODETABLE_ITEM_ID() {
        return CODETABLE_ITEM_ID;
    }

    public void setCODETABLE_ITEM_ID(String CODETABLE_ITEM_ID) {
        this.CODETABLE_ITEM_ID = CODETABLE_ITEM_ID;
    }

    public String getDISPLAY_NAME_ZH() {
        return DISPLAY_NAME_ZH;
    }

    public void setDISPLAY_NAME_ZH(String DISPLAY_NAME_ZH) {
        this.DISPLAY_NAME_ZH = DISPLAY_NAME_ZH;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CODETABLE_ITEM_ID);
        dest.writeString(this.DISPLAY_NAME_ZH);
    }

    public SellType() {
    }

    protected SellType(Parcel in) {
        this.CODETABLE_ITEM_ID = in.readString();
        this.DISPLAY_NAME_ZH = in.readString();
    }

    public static final Parcelable.Creator<SellType> CREATOR = new Parcelable.Creator<SellType>() {
        @Override
        public SellType createFromParcel(Parcel source) {
            return new SellType(source);
        }

        @Override
        public SellType[] newArray(int size) {
            return new SellType[size];
        }
    };
}
