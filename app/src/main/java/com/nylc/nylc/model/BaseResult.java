package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.nylc.nylc.xutilsjsonparser.JsonParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * 基础结果类，返回结果都可以用这个来接收
 * Created by 吴曰阳 on 2018/3/1.
 */

@HttpResponse(parser = JsonParser.class)
public class BaseResult implements Parcelable {
    private String level;
    private String method;
    private String code;
    private String msg;
    private String data;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BaseResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.level);
        dest.writeString(this.method);
        dest.writeString(this.code);
        dest.writeString(this.msg);
        dest.writeString(this.data);
    }

    protected BaseResult(Parcel in) {
        this.level = in.readString();
        this.method = in.readString();
        this.code = in.readString();
        this.msg = in.readString();
        this.data = in.readString();
    }

    public static final Creator<BaseResult> CREATOR = new Creator<BaseResult>() {
        @Override
        public BaseResult createFromParcel(Parcel source) {
            return new BaseResult(source);
        }

        @Override
        public BaseResult[] newArray(int size) {
            return new BaseResult[size];
        }
    };
}
