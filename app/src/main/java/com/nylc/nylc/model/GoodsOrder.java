package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/3/28.
 */

public class GoodsOrder implements Parcelable {

    /**
     * CITY : 金华
     * CITY_ID : 28c3355340f1462388b9e541ee28bcf2
     * COUNTY : 义乌
     * COUNTY_ID : c746e017e658410e80fb947d33439f23
     * CREATED_BY : 丙队长
     * CREATED_BY_ID : 12ff6ebd46704100aeece3847bdba323
     * CREATED_DATE : 2018-04-16 22:28:06
     * CREATED_DEPT : 丙组
     * CREATED_DEPT_ID : d101cd98b64f4d82bccc116316b0e2e6
     * FARMER_ID : 12ff6ebd46704100aeece3847bdba323
     * FARMER_NAME : 丙队长
     * ID : 1dbafc955c2d41aab7dee3d3d285f12e
     * MODIFIED_BY : 丙队长
     * MODIFIED_BY_ID : 12ff6ebd46704100aeece3847bdba323
     * MODIFIED_DATE : 2018-04-16 22:28:06
     * PRODUCT_TYPE : 小麦
     * PRODUCT_TYPE_ID : PRODUCT_TYPE_8536149027
     * PROVINCE : 浙江省
     * PROVINCE_ID : ba8783f2af5a4d7dae3f6546af4920e6
     * QUANTITY : 16
     * ROWNUM_ : 1
     * STATUS : 0
     * SUBSCRIPTION : 300
     * TOWN : 稠城街道
     * TOWN_ID : 2b1b683c78e94ac6ab942719d207d3de
     * VILLAGE : 森屋村
     * VILLAGE_ID : 670fd03ae25d4027975e25d658abe429
     */

    private String AMOUNT;
    private String CITY;
    private String CITY_ID;
    private String COUNTY;
    private String COUNTY_ID;
    private String CREATED_BY;
    private String CREATED_BY_ID;
    private String CREATED_DATE;
    private String CREATED_DEPT;
    private String CREATED_DEPT_ID;
    private String FARMER_ID;
    private String FARMER_NAME;
    private String ID;
    private String MODIFIED_BY;
    private String MODIFIED_BY_ID;
    private String MODIFIED_DATE;
    private String PRODUCT_TYPE;
    private String PRODUCT_TYPE_ID;
    private String PROVINCE;
    private String PROVINCE_ID;
    private int QUANTITY;
    private int ROWNUM_;
    private int STATUS;
    private int SUBSCRIPTION;
    private String TOWN;
    private String TOWN_ID;
    private String VILLAGE;
    private String VILLAGE_ID;

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getCOUNTY() {
        return COUNTY;
    }

    public void setCOUNTY(String COUNTY) {
        this.COUNTY = COUNTY;
    }

    public String getCOUNTY_ID() {
        return COUNTY_ID;
    }

    public void setCOUNTY_ID(String COUNTY_ID) {
        this.COUNTY_ID = COUNTY_ID;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public String getCREATED_BY_ID() {
        return CREATED_BY_ID;
    }

    public void setCREATED_BY_ID(String CREATED_BY_ID) {
        this.CREATED_BY_ID = CREATED_BY_ID;
    }

    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(String CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    public String getCREATED_DEPT() {
        return CREATED_DEPT;
    }

    public void setCREATED_DEPT(String CREATED_DEPT) {
        this.CREATED_DEPT = CREATED_DEPT;
    }

    public String getCREATED_DEPT_ID() {
        return CREATED_DEPT_ID;
    }

    public void setCREATED_DEPT_ID(String CREATED_DEPT_ID) {
        this.CREATED_DEPT_ID = CREATED_DEPT_ID;
    }

    public String getFARMER_ID() {
        return FARMER_ID;
    }

    public void setFARMER_ID(String FARMER_ID) {
        this.FARMER_ID = FARMER_ID;
    }

    public String getFARMER_NAME() {
        return FARMER_NAME;
    }

    public void setFARMER_NAME(String FARMER_NAME) {
        this.FARMER_NAME = FARMER_NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMODIFIED_BY() {
        return MODIFIED_BY;
    }

    public void setMODIFIED_BY(String MODIFIED_BY) {
        this.MODIFIED_BY = MODIFIED_BY;
    }

    public String getMODIFIED_BY_ID() {
        return MODIFIED_BY_ID;
    }

    public void setMODIFIED_BY_ID(String MODIFIED_BY_ID) {
        this.MODIFIED_BY_ID = MODIFIED_BY_ID;
    }

    public String getMODIFIED_DATE() {
        return MODIFIED_DATE;
    }

    public void setMODIFIED_DATE(String MODIFIED_DATE) {
        this.MODIFIED_DATE = MODIFIED_DATE;
    }

    public String getPRODUCT_TYPE() {
        return PRODUCT_TYPE;
    }

    public void setPRODUCT_TYPE(String PRODUCT_TYPE) {
        this.PRODUCT_TYPE = PRODUCT_TYPE;
    }

    public String getPRODUCT_TYPE_ID() {
        return PRODUCT_TYPE_ID;
    }

    public void setPRODUCT_TYPE_ID(String PRODUCT_TYPE_ID) {
        this.PRODUCT_TYPE_ID = PRODUCT_TYPE_ID;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getPROVINCE_ID() {
        return PROVINCE_ID;
    }

    public void setPROVINCE_ID(String PROVINCE_ID) {
        this.PROVINCE_ID = PROVINCE_ID;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public int getROWNUM_() {
        return ROWNUM_;
    }

    public void setROWNUM_(int ROWNUM_) {
        this.ROWNUM_ = ROWNUM_;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public int getSUBSCRIPTION() {
        return SUBSCRIPTION;
    }

    public void setSUBSCRIPTION(int SUBSCRIPTION) {
        this.SUBSCRIPTION = SUBSCRIPTION;
    }

    public String getTOWN() {
        return TOWN;
    }

    public void setTOWN(String TOWN) {
        this.TOWN = TOWN;
    }

    public String getTOWN_ID() {
        return TOWN_ID;
    }

    public void setTOWN_ID(String TOWN_ID) {
        this.TOWN_ID = TOWN_ID;
    }

    public String getVILLAGE() {
        return VILLAGE;
    }

    public void setVILLAGE(String VILLAGE) {
        this.VILLAGE = VILLAGE;
    }

    public String getVILLAGE_ID() {
        return VILLAGE_ID;
    }

    public void setVILLAGE_ID(String VILLAGE_ID) {
        this.VILLAGE_ID = VILLAGE_ID;
    }

    public GoodsOrder() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AMOUNT);
        dest.writeString(this.CITY);
        dest.writeString(this.CITY_ID);
        dest.writeString(this.COUNTY);
        dest.writeString(this.COUNTY_ID);
        dest.writeString(this.CREATED_BY);
        dest.writeString(this.CREATED_BY_ID);
        dest.writeString(this.CREATED_DATE);
        dest.writeString(this.CREATED_DEPT);
        dest.writeString(this.CREATED_DEPT_ID);
        dest.writeString(this.FARMER_ID);
        dest.writeString(this.FARMER_NAME);
        dest.writeString(this.ID);
        dest.writeString(this.MODIFIED_BY);
        dest.writeString(this.MODIFIED_BY_ID);
        dest.writeString(this.MODIFIED_DATE);
        dest.writeString(this.PRODUCT_TYPE);
        dest.writeString(this.PRODUCT_TYPE_ID);
        dest.writeString(this.PROVINCE);
        dest.writeString(this.PROVINCE_ID);
        dest.writeInt(this.QUANTITY);
        dest.writeInt(this.ROWNUM_);
        dest.writeInt(this.STATUS);
        dest.writeInt(this.SUBSCRIPTION);
        dest.writeString(this.TOWN);
        dest.writeString(this.TOWN_ID);
        dest.writeString(this.VILLAGE);
        dest.writeString(this.VILLAGE_ID);
    }

    protected GoodsOrder(Parcel in) {
        this.AMOUNT = in.readString();
        this.CITY = in.readString();
        this.CITY_ID = in.readString();
        this.COUNTY = in.readString();
        this.COUNTY_ID = in.readString();
        this.CREATED_BY = in.readString();
        this.CREATED_BY_ID = in.readString();
        this.CREATED_DATE = in.readString();
        this.CREATED_DEPT = in.readString();
        this.CREATED_DEPT_ID = in.readString();
        this.FARMER_ID = in.readString();
        this.FARMER_NAME = in.readString();
        this.ID = in.readString();
        this.MODIFIED_BY = in.readString();
        this.MODIFIED_BY_ID = in.readString();
        this.MODIFIED_DATE = in.readString();
        this.PRODUCT_TYPE = in.readString();
        this.PRODUCT_TYPE_ID = in.readString();
        this.PROVINCE = in.readString();
        this.PROVINCE_ID = in.readString();
        this.QUANTITY = in.readInt();
        this.ROWNUM_ = in.readInt();
        this.STATUS = in.readInt();
        this.SUBSCRIPTION = in.readInt();
        this.TOWN = in.readString();
        this.TOWN_ID = in.readString();
        this.VILLAGE = in.readString();
        this.VILLAGE_ID = in.readString();
    }

    public static final Creator<GoodsOrder> CREATOR = new Creator<GoodsOrder>() {
        @Override
        public GoodsOrder createFromParcel(Parcel source) {
            return new GoodsOrder(source);
        }

        @Override
        public GoodsOrder[] newArray(int size) {
            return new GoodsOrder[size];
        }
    };
}
