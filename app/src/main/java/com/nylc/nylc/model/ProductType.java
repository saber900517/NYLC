package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品类型
 * Created by 吴曰阳 on 2018/3/1.
 */

public class ProductType implements Parcelable {

    /**
     * CODETABLE_ITEM_ID : PRODUCTS_TYPE_ZZ
     * DISPLAY_NAME_ZH : 种子
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

    public ProductType() {
    }

    protected ProductType(Parcel in) {
        this.CODETABLE_ITEM_ID = in.readString();
        this.DISPLAY_NAME_ZH = in.readString();
    }

    public static final Parcelable.Creator<ProductType> CREATOR = new Parcelable.Creator<ProductType>() {
        @Override
        public ProductType createFromParcel(Parcel source) {
            return new ProductType(source);
        }

        @Override
        public ProductType[] newArray(int size) {
            return new ProductType[size];
        }
    };

    @Override
    public String toString() {
        return DISPLAY_NAME_ZH;
    }
}
