package com.nylc.nylc.eventbus;

/**
 * Created by kasim on 2018/4/25.
 */

public class RefreshEvent {
    private String action;

    public RefreshEvent() {
    }

    public RefreshEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
