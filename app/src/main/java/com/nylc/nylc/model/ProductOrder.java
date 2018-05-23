package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/4/21.
 */

public class ProductOrder implements Parcelable {

    /**
     * CITY : 金华
     * CITY_ID : 28c3355340f1462388b9e541ee28bcf2
     * COMPANY_ID : b9d1216d11f8463a9801ae021917849b
     * COMPANY_NAME : platform
     * COUNTY : 义乌
     * COUNTY_ID : c746e017e658410e80fb947d33439f23
     * CREATED_BY : 丙队长
     * CREATED_BY_ID : 12ff6ebd46704100aeece3847bdba323
     * CREATED_DATE : 2018-04-21 09:37:57
     * CREATED_DEPT : 丙组
     * CREATED_DEPT_ID : d101cd98b64f4d82bccc116316b0e2e6
     * FARMER_ID : 12ff6ebd46704100aeece3847bdba323
     * FARMER_NAME : 丙队长
     * GROUP_ID : 8c1d7f79a3ac405aa32875c26c3d1aa0
     * ID : 8d795bb515fe49a8ae6ce8fbbd3881da
     * MODIFIED_BY : 丙队长
     * MODIFIED_BY_ID : 12ff6ebd46704100aeece3847bdba323
     * MODIFIED_DATE : 2018-04-21 09:37:57
     * ORDER_VILLAGE_ID : e3278932cfa54577b8605583844d71b3
     * PRICE : 1.2
     * PRODUCT_TYPE : 小麦
     * PRODUCT_TYPE_ID : PRODUCT_TYPE_8536149027
     * PROVINCE : 浙江省
     * PROVINCE_ID : ba8783f2af5a4d7dae3f6546af4920e6
     * QUANTITY : 200
     * ROWNUM_ : 1
     * SELL_TYPE : 预定
     * SELL_TYPE_ID : SELL_TYPE_2316598470
     * STATUS : 10
     * SUBSCRIPTION : 0
     * TOWN : 稠城街道
     * TOWN_ID : 2b1b683c78e94ac6ab942719d207d3de
     * VILLAGE : 森屋村
     * VILLAGE_ID : 670fd03ae25d4027975e25d658abe429
     * WARTER : 15
     */

    private String AMOUNT;
    private String CITY;
    private String CITY_ID;
    private String COMPANY_ID;
    private String COMPANY_NAME;
    private String COUNTY;
    private String COUNTY_ID;
    private String CREATED_BY;
    private String CREATED_BY_ID;
    private String CREATED_DATE;
    private String CREATED_DEPT;
    private String CREATED_DEPT_ID;
    private String FARMER_ID;
    private String FARMER_NAME;
    private String GROUP_ID;
    private String ID;
    private String MODIFIED_BY;
    private String MODIFIED_BY_ID;
    private String MODIFIED_DATE;
    private String ORDER_VILLAGE_ID;
    private String PRICE;
    private String PRODUCT_TYPE;
    private String PRODUCT_TYPE_ID;
    private String PROVINCE;
    private String PROVINCE_ID;
    private int QUANTITY;
    private int ROWNUM_;
    private String mostQuote;
    private String myQuote;
    private String SELL_TYPE;
    private String SELL_TYPE_ID;
    private int STATUS;
    private int SUBSCRIPTION;
    private String TOWN;
    private String TOWN_ID;
    private String VILLAGE;
    private String VILLAGE_ID;
    private int WARTER;
    private String QUANTITY_JIN;


    public String getQUANTITY_JIN() {
        return QUANTITY_JIN;
    }

    public void setQUANTITY_JIN(String QUANTITY_JIN) {
        this.QUANTITY_JIN = QUANTITY_JIN;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getMostQuote() {
        return mostQuote;
    }

    public void setMostQuote(String mostQuote) {
        this.mostQuote = mostQuote;
    }

    public String getMyQuote() {
        return myQuote;
    }

    public void setMyQuote(String myQuote) {
        this.myQuote = myQuote;
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

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
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

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
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

    public String getORDER_VILLAGE_ID() {
        return ORDER_VILLAGE_ID;
    }

    public void setORDER_VILLAGE_ID(String ORDER_VILLAGE_ID) {
        this.ORDER_VILLAGE_ID = ORDER_VILLAGE_ID;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
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

    public String getSELL_TYPE() {
        return SELL_TYPE;
    }

    public void setSELL_TYPE(String SELL_TYPE) {
        this.SELL_TYPE = SELL_TYPE;
    }

    public String getSELL_TYPE_ID() {
        return SELL_TYPE_ID;
    }

    public void setSELL_TYPE_ID(String SELL_TYPE_ID) {
        this.SELL_TYPE_ID = SELL_TYPE_ID;
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

    public int getWARTER() {
        return WARTER;
    }

    public void setWARTER(int WARTER) {
        this.WARTER = WARTER;
    }

    public ProductOrder() {
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
        dest.writeString(this.COMPANY_ID);
        dest.writeString(this.COMPANY_NAME);
        dest.writeString(this.COUNTY);
        dest.writeString(this.COUNTY_ID);
        dest.writeString(this.CREATED_BY);
        dest.writeString(this.CREATED_BY_ID);
        dest.writeString(this.CREATED_DATE);
        dest.writeString(this.CREATED_DEPT);
        dest.writeString(this.CREATED_DEPT_ID);
        dest.writeString(this.FARMER_ID);
        dest.writeString(this.FARMER_NAME);
        dest.writeString(this.GROUP_ID);
        dest.writeString(this.ID);
        dest.writeString(this.MODIFIED_BY);
        dest.writeString(this.MODIFIED_BY_ID);
        dest.writeString(this.MODIFIED_DATE);
        dest.writeString(this.ORDER_VILLAGE_ID);
        dest.writeString(this.PRICE);
        dest.writeString(this.PRODUCT_TYPE);
        dest.writeString(this.PRODUCT_TYPE_ID);
        dest.writeString(this.PROVINCE);
        dest.writeString(this.PROVINCE_ID);
        dest.writeInt(this.QUANTITY);
        dest.writeInt(this.ROWNUM_);
        dest.writeString(this.mostQuote);
        dest.writeString(this.myQuote);
        dest.writeString(this.SELL_TYPE);
        dest.writeString(this.SELL_TYPE_ID);
        dest.writeInt(this.STATUS);
        dest.writeInt(this.SUBSCRIPTION);
        dest.writeString(this.TOWN);
        dest.writeString(this.TOWN_ID);
        dest.writeString(this.VILLAGE);
        dest.writeString(this.VILLAGE_ID);
        dest.writeInt(this.WARTER);
        dest.writeString(this.QUANTITY_JIN);
    }

    protected ProductOrder(Parcel in) {
        this.AMOUNT = in.readString();
        this.CITY = in.readString();
        this.CITY_ID = in.readString();
        this.COMPANY_ID = in.readString();
        this.COMPANY_NAME = in.readString();
        this.COUNTY = in.readString();
        this.COUNTY_ID = in.readString();
        this.CREATED_BY = in.readString();
        this.CREATED_BY_ID = in.readString();
        this.CREATED_DATE = in.readString();
        this.CREATED_DEPT = in.readString();
        this.CREATED_DEPT_ID = in.readString();
        this.FARMER_ID = in.readString();
        this.FARMER_NAME = in.readString();
        this.GROUP_ID = in.readString();
        this.ID = in.readString();
        this.MODIFIED_BY = in.readString();
        this.MODIFIED_BY_ID = in.readString();
        this.MODIFIED_DATE = in.readString();
        this.ORDER_VILLAGE_ID = in.readString();
        this.PRICE = in.readString();
        this.PRODUCT_TYPE = in.readString();
        this.PRODUCT_TYPE_ID = in.readString();
        this.PROVINCE = in.readString();
        this.PROVINCE_ID = in.readString();
        this.QUANTITY = in.readInt();
        this.ROWNUM_ = in.readInt();
        this.mostQuote = in.readString();
        this.myQuote = in.readString();
        this.SELL_TYPE = in.readString();
        this.SELL_TYPE_ID = in.readString();
        this.STATUS = in.readInt();
        this.SUBSCRIPTION = in.readInt();
        this.TOWN = in.readString();
        this.TOWN_ID = in.readString();
        this.VILLAGE = in.readString();
        this.VILLAGE_ID = in.readString();
        this.WARTER = in.readInt();
        this.QUANTITY_JIN = in.readString();
    }

    public static final Creator<ProductOrder> CREATOR = new Creator<ProductOrder>() {
        @Override
        public ProductOrder createFromParcel(Parcel source) {
            return new ProductOrder(source);
        }

        @Override
        public ProductOrder[] newArray(int size) {
            return new ProductOrder[size];
        }
    };
}
