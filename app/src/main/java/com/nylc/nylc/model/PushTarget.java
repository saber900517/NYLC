package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 推送目标
 * Created by 吴曰阳 on 2018/3/1.
 */

public class PushTarget implements Parcelable {

    /**
     * FARMER_ID : 7a848cbc0b4c4ec49f83f42661d9fd0e
     * ID : 17b840e3c5e049fc9d9999110b5d282b
     * IS_READED : 0
     * LEADER_ID : e7ee227fb971497d8ace9cabfc485d28
     * LEADER_NAME : 甲队长
     * NODE_ID : 4371a18f23e74cbbbeb1c39909f717e6
     * PUBLISH_TIME : 2018-03-01 18:10:09
     */

    private String FARMER_ID;
    private String ID;
    private String IS_READED;
    private String LEADER_ID;
    private String LEADER_NAME;
    private String NODE_ID;
    private String PUBLISH_TIME;

    public String getFARMER_ID() {
        return FARMER_ID;
    }

    public void setFARMER_ID(String FARMER_ID) {
        this.FARMER_ID = FARMER_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIS_READED() {
        return IS_READED;
    }

    public void setIS_READED(String IS_READED) {
        this.IS_READED = IS_READED;
    }

    public String getLEADER_ID() {
        return LEADER_ID;
    }

    public void setLEADER_ID(String LEADER_ID) {
        this.LEADER_ID = LEADER_ID;
    }

    public String getLEADER_NAME() {
        return LEADER_NAME;
    }

    public void setLEADER_NAME(String LEADER_NAME) {
        this.LEADER_NAME = LEADER_NAME;
    }

    public String getNODE_ID() {
        return NODE_ID;
    }

    public void setNODE_ID(String NODE_ID) {
        this.NODE_ID = NODE_ID;
    }

    public String getPUBLISH_TIME() {
        return PUBLISH_TIME;
    }

    public void setPUBLISH_TIME(String PUBLISH_TIME) {
        this.PUBLISH_TIME = PUBLISH_TIME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FARMER_ID);
        dest.writeString(this.ID);
        dest.writeString(this.IS_READED);
        dest.writeString(this.LEADER_ID);
        dest.writeString(this.LEADER_NAME);
        dest.writeString(this.NODE_ID);
        dest.writeString(this.PUBLISH_TIME);
    }

    public PushTarget() {
    }

    protected PushTarget(Parcel in) {
        this.FARMER_ID = in.readString();
        this.ID = in.readString();
        this.IS_READED = in.readString();
        this.LEADER_ID = in.readString();
        this.LEADER_NAME = in.readString();
        this.NODE_ID = in.readString();
        this.PUBLISH_TIME = in.readString();
    }

    public static final Parcelable.Creator<PushTarget> CREATOR = new Parcelable.Creator<PushTarget>() {
        @Override
        public PushTarget createFromParcel(Parcel source) {
            return new PushTarget(source);
        }

        @Override
        public PushTarget[] newArray(int size) {
            return new PushTarget[size];
        }
    };
}
