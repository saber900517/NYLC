package com.nylc.nylc.eventbus;

/**
 * Created by 吴曰阳 on 2018/3/13.
 */

public class DeleteGoodsEvent {
    private int goodsId;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
