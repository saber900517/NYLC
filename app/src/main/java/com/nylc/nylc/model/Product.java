package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 产品信息
 * Created by 吴曰阳 on 2018/3/1.
 */

public class Product implements Parcelable {

    /**
     * GOODS_DESCRIPTION : null
     * GOODS_ID : a9b1d1cb6a374d08901de11091623a53
     * GOODS_NAME : testName2
     * GOODS_PICTURE : null
     * GOODS_PRICE : 11.11
     * GOODS_TYPE : 2
     * MODIFY_TIME : 2018-02-22 22:53:09
     * STATUS : 1
     * SUPPLIER_ID : 2
     * SUPPLIER_NAME : 阿桑
     */

    private String GOODS_DESCRIPTION;
    private String GOODS_ID;
    private String GOODS_NAME;
    private String GOODS_PICTURE;
    private String GOODS_PRICE;
    private String GOODS_TYPE;
    private String MODIFY_TIME;
    private int STATUS;
    private String SUPPLIER_ID;
    private String SUPPLIER_NAME;

    public String getGOODS_DESCRIPTION() {
        return GOODS_DESCRIPTION;
    }

    public void setGOODS_DESCRIPTION(String GOODS_DESCRIPTION) {
        this.GOODS_DESCRIPTION = GOODS_DESCRIPTION;
    }

    public String getGOODS_ID() {
        return GOODS_ID;
    }

    public void setGOODS_ID(String GOODS_ID) {
        this.GOODS_ID = GOODS_ID;
    }

    public String getGOODS_NAME() {
        return GOODS_NAME;
    }

    public void setGOODS_NAME(String GOODS_NAME) {
        this.GOODS_NAME = GOODS_NAME;
    }

    public String getGOODS_PICTURE() {
        return GOODS_PICTURE;
    }

    public void setGOODS_PICTURE(String GOODS_PICTURE) {
        this.GOODS_PICTURE = GOODS_PICTURE;
    }

    public String getGOODS_PRICE() {
        return GOODS_PRICE;
    }

    public void setGOODS_PRICE(String GOODS_PRICE) {
        this.GOODS_PRICE = GOODS_PRICE;
    }

    public String getGOODS_TYPE() {
        return GOODS_TYPE;
    }

    public void setGOODS_TYPE(String GOODS_TYPE) {
        this.GOODS_TYPE = GOODS_TYPE;
    }

    public String getMODIFY_TIME() {
        return MODIFY_TIME;
    }

    public void setMODIFY_TIME(String MODIFY_TIME) {
        this.MODIFY_TIME = MODIFY_TIME;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getSUPPLIER_ID() {
        return SUPPLIER_ID;
    }

    public void setSUPPLIER_ID(String SUPPLIER_ID) {
        this.SUPPLIER_ID = SUPPLIER_ID;
    }

    public String getSUPPLIER_NAME() {
        return SUPPLIER_NAME;
    }

    public void setSUPPLIER_NAME(String SUPPLIER_NAME) {
        this.SUPPLIER_NAME = SUPPLIER_NAME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.GOODS_DESCRIPTION);
        dest.writeString(this.GOODS_ID);
        dest.writeString(this.GOODS_NAME);
        dest.writeString(this.GOODS_PICTURE);
        dest.writeString(this.GOODS_PRICE);
        dest.writeString(this.GOODS_TYPE);
        dest.writeString(this.MODIFY_TIME);
        dest.writeInt(this.STATUS);
        dest.writeString(this.SUPPLIER_ID);
        dest.writeString(this.SUPPLIER_NAME);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.GOODS_DESCRIPTION = in.readString();
        this.GOODS_ID = in.readString();
        this.GOODS_NAME = in.readString();
        this.GOODS_PICTURE = in.readString();
        this.GOODS_PRICE = in.readString();
        this.GOODS_TYPE = in.readString();
        this.MODIFY_TIME = in.readString();
        this.STATUS = in.readInt();
        this.SUPPLIER_ID = in.readString();
        this.SUPPLIER_NAME = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
