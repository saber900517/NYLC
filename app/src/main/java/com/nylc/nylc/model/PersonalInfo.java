package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/5/6.
 */

public class PersonalInfo implements Parcelable {

    /**
     * ACCOUNT_ID : e6457db4baa04b70a45936367739884a
     * ACCOUNT_NAME : 15726126291
     * EMPNAME : 丙队长
     * EMPTYPEID : EMP_TYPE_2549180673
     * EMPTYPENAME : 小组长
     * LOGIN_TIME : 2018-05-06 12:18:37
     * NODEID : d101cd98b64f4d82bccc116316b0e2e6
     * PERSON_ID : 12ff6ebd46704100aeece3847bdba323
     * TOKEN_KEY : 3485a27aae
     * VERIFIED : 1
     */

    private String ACCOUNT_ID;
    private String ACCOUNT_NAME;
    private String EMPNAME;
    private String EMPTYPEID;
    private String EMPTYPENAME;
    private String LOGIN_TIME;
    private String NODEID;
    private String PERSON_ID;
    private String TOKEN_KEY;
    private String VERIFIED;


    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public void setACCOUNT_ID(String ACCOUNT_ID) {
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String ACCOUNT_NAME) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
    }

    public String getEMPNAME() {
        return EMPNAME;
    }

    public void setEMPNAME(String EMPNAME) {
        this.EMPNAME = EMPNAME;
    }

    public String getEMPTYPEID() {
        return EMPTYPEID;
    }

    public void setEMPTYPEID(String EMPTYPEID) {
        this.EMPTYPEID = EMPTYPEID;
    }

    public String getEMPTYPENAME() {
        return EMPTYPENAME;
    }

    public void setEMPTYPENAME(String EMPTYPENAME) {
        this.EMPTYPENAME = EMPTYPENAME;
    }

    public String getLOGIN_TIME() {
        return LOGIN_TIME;
    }

    public void setLOGIN_TIME(String LOGIN_TIME) {
        this.LOGIN_TIME = LOGIN_TIME;
    }

    public String getNODEID() {
        return NODEID;
    }

    public void setNODEID(String NODEID) {
        this.NODEID = NODEID;
    }

    public String getPERSON_ID() {
        return PERSON_ID;
    }

    public void setPERSON_ID(String PERSON_ID) {
        this.PERSON_ID = PERSON_ID;
    }

    public String getTOKEN_KEY() {
        return TOKEN_KEY;
    }

    public void setTOKEN_KEY(String TOKEN_KEY) {
        this.TOKEN_KEY = TOKEN_KEY;
    }

    public String getVERIFIED() {
        return VERIFIED;
    }

    public void setVERIFIED(String VERIFIED) {
        this.VERIFIED = VERIFIED;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ACCOUNT_ID);
        dest.writeString(this.ACCOUNT_NAME);
        dest.writeString(this.EMPNAME);
        dest.writeString(this.EMPTYPEID);
        dest.writeString(this.EMPTYPENAME);
        dest.writeString(this.LOGIN_TIME);
        dest.writeString(this.NODEID);
        dest.writeString(this.PERSON_ID);
        dest.writeString(this.TOKEN_KEY);
        dest.writeString(this.VERIFIED);
    }

    public PersonalInfo() {
    }

    protected PersonalInfo(Parcel in) {
        this.ACCOUNT_ID = in.readString();
        this.ACCOUNT_NAME = in.readString();
        this.EMPNAME = in.readString();
        this.EMPTYPEID = in.readString();
        this.EMPTYPENAME = in.readString();
        this.LOGIN_TIME = in.readString();
        this.NODEID = in.readString();
        this.PERSON_ID = in.readString();
        this.TOKEN_KEY = in.readString();
        this.VERIFIED = in.readString();
    }

    public static final Parcelable.Creator<PersonalInfo> CREATOR = new Parcelable.Creator<PersonalInfo>() {
        @Override
        public PersonalInfo createFromParcel(Parcel source) {
            return new PersonalInfo(source);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };
}
