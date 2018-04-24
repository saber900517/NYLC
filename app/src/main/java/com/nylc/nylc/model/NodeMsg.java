package com.nylc.nylc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kasim on 2018/4/22.
 */

public class NodeMsg implements Parcelable {

    /**
     * DISPLAY_NAME_ZH : 甲组
     * NODE_ID : 4371a18f23e74cbbbeb1c39909f717e6
     * NODE_LEVEL : 7
     * PARENT_NODE_ID : 670fd03ae25d4027975e25d658abe429
     */

    private String DISPLAY_NAME_ZH;
    private String NODE_ID;
    private int NODE_LEVEL;
    private String PARENT_NODE_ID;


    public String getDISPLAY_NAME_ZH() {
        return DISPLAY_NAME_ZH;
    }

    public void setDISPLAY_NAME_ZH(String DISPLAY_NAME_ZH) {
        this.DISPLAY_NAME_ZH = DISPLAY_NAME_ZH;
    }

    public String getNODE_ID() {
        return NODE_ID;
    }

    public void setNODE_ID(String NODE_ID) {
        this.NODE_ID = NODE_ID;
    }

    public int getNODE_LEVEL() {
        return NODE_LEVEL;
    }

    public void setNODE_LEVEL(int NODE_LEVEL) {
        this.NODE_LEVEL = NODE_LEVEL;
    }

    public String getPARENT_NODE_ID() {
        return PARENT_NODE_ID;
    }

    public void setPARENT_NODE_ID(String PARENT_NODE_ID) {
        this.PARENT_NODE_ID = PARENT_NODE_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.DISPLAY_NAME_ZH);
        dest.writeString(this.NODE_ID);
        dest.writeInt(this.NODE_LEVEL);
        dest.writeString(this.PARENT_NODE_ID);
    }

    public NodeMsg() {
    }

    protected NodeMsg(Parcel in) {
        this.DISPLAY_NAME_ZH = in.readString();
        this.NODE_ID = in.readString();
        this.NODE_LEVEL = in.readInt();
        this.PARENT_NODE_ID = in.readString();
    }

    public static final Parcelable.Creator<NodeMsg> CREATOR = new Parcelable.Creator<NodeMsg>() {
        @Override
        public NodeMsg createFromParcel(Parcel source) {
            return new NodeMsg(source);
        }

        @Override
        public NodeMsg[] newArray(int size) {
            return new NodeMsg[size];
        }
    };

    @Override
    public String toString() {
        return DISPLAY_NAME_ZH;
    }
}
