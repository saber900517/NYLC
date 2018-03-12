package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录结果类
 * Created by 吴曰阳 on 2017/12/9.
 */

public class Login implements Parcelable {


    /**
     * tokenKey : 33324148da
     * empTypeName : 供应商
     * empTypeId : EMP_TYPE_4507269381
     */

    private String tokenKey;
    private String empTypeName;
    private String empTypeId;

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getEmpTypeName() {
        return empTypeName;
    }

    public void setEmpTypeName(String empTypeName) {
        this.empTypeName = empTypeName;
    }

    public String getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(String empTypeId) {
        this.empTypeId = empTypeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tokenKey);
        dest.writeString(this.empTypeName);
        dest.writeString(this.empTypeId);
    }

    public Login() {
    }

    protected Login(Parcel in) {
        this.tokenKey = in.readString();
        this.empTypeName = in.readString();
        this.empTypeId = in.readString();
    }

    public static final Parcelable.Creator<Login> CREATOR = new Parcelable.Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel source) {
            return new Login(source);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };
}
