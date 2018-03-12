package com.nylc.nylc.eventbus;

/**
 * 更新商品状态
 * Created by 吴曰阳 on 2018/3/13.
 */

public class UpdateGoodsStateEvent {
    private int goodsId;
    private int status;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
