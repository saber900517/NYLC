package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 上传图片返回结果
 * Created by 吴曰阳 on 2018/3/1.
 */

public class UploadImage implements Parcelable {

    /**
     * imageNewName : 图片test11499599138558.gif
     * imagePath : http://47.93.29.50:8001/pic/
     */

    private String imageNewName;
    private String imagePath;

    public String getImageNewName() {
        return imageNewName;
    }

    public void setImageNewName(String imageNewName) {
        this.imageNewName = imageNewName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageNewName);
        dest.writeString(this.imagePath);
    }

    public UploadImage() {
    }

    protected UploadImage(Parcel in) {
        this.imageNewName = in.readString();
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<UploadImage> CREATOR = new Parcelable.Creator<UploadImage>() {
        @Override
        public UploadImage createFromParcel(Parcel source) {
            return new UploadImage(source);
        }

        @Override
        public UploadImage[] newArray(int size) {
            return new UploadImage[size];
        }
    };
}
