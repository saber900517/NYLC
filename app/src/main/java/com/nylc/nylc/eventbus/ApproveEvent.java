package com.nylc.nylc.eventbus;

import com.nylc.nylc.model.ApproveSale;

/**
 * Created by kasim on 2018/5/7.
 */

public class ApproveEvent {
    public static final int STATE_GOODS = 1;
    public static final int STATE_PRODUCTS = 2;
    private ApproveSale approveSale;
    private int position;
    private int refreshType;

    public ApproveSale getApproveSale() {
        return approveSale;
    }

    public void setApproveSale(ApproveSale approveSale) {
        this.approveSale = approveSale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRefreshType() {
        return refreshType;
    }

    public void setRefreshType(int refreshType) {
        this.refreshType = refreshType;
    }
}
