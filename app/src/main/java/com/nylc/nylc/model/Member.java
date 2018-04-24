package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 成员类
 * Created by 吴曰阳 on 2018/3/1.
 */

public class Member implements Parcelable {

    /**
     * ACCOUNT_ID : f950cd18fd89416ba0593b8b78fde6c5
     * EMP_ID : 7a848cbc0b4c4ec49f83f42661d9fd0e
     * EMPNAME : 甲农民1
     * EMPTYPENAME : 农民
     * NODE_ID : 4371a18f23e74cbbbeb1c39909f717e6
     * RECORD_ID : 2c9f8be8610dd8d101610e0998b90007
     */

    private String ACCOUNT_ID;
    private String EMP_ID;
    private String EMPNAME;
    private String EMPTYPENAME;
    private String NODE_ID;
    private String RECORD_ID;


    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public void setACCOUNT_ID(String ACCOUNT_ID) {
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public String getEMP_ID() {
        return EMP_ID;
    }

    public void setEMP_ID(String EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public String getEMPNAME() {
        return EMPNAME;
    }

    public void setEMPNAME(String EMPNAME) {
        this.EMPNAME = EMPNAME;
    }

    public String getEMPTYPENAME() {
        return EMPTYPENAME;
    }

    public void setEMPTYPENAME(String EMPTYPENAME) {
        this.EMPTYPENAME = EMPTYPENAME;
    }

    public String getNODE_ID() {
        return NODE_ID;
    }

    public void setNODE_ID(String NODE_ID) {
        this.NODE_ID = NODE_ID;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ACCOUNT_ID);
        dest.writeString(this.EMP_ID);
        dest.writeString(this.EMPNAME);
        dest.writeString(this.EMPTYPENAME);
        dest.writeString(this.NODE_ID);
        dest.writeString(this.RECORD_ID);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.ACCOUNT_ID = in.readString();
        this.EMP_ID = in.readString();
        this.EMPNAME = in.readString();
        this.EMPTYPENAME = in.readString();
        this.NODE_ID = in.readString();
        this.RECORD_ID = in.readString();
    }

    public static final Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public String toString() {
        return this.EMPNAME;
    }
}
