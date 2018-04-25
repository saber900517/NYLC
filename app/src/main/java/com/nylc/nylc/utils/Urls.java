package com.nylc.nylc.utils;

/**
 * 请求接口地址
 * Created by 吴曰阳 on 2017/12/9.
 */

public class Urls {
    private static String ROOT = "http://47.98.51.252:8081/nylc";

    public static String IMG = "http://47.98.51.252:8081/nylcpic/";
    public static String LOGIN = ROOT + "/user/loginAction";
    public static String REGISTER = ROOT + "/user/registerAction";
    //登录用户
    public static String loginAction = ROOT + "/user/loginAction";
    //退出登录
    public static String logOutAction = ROOT + "/user/logOutAction";
    //忘记密码
    public static String forgetPassAction = ROOT + "/user/forgetPassAction";
    //修改密码
    public static String modifyPassAction = ROOT + "/user/modifyPassAction";
    //发送验证码
    public static String sendCodeAction = ROOT + "/auth/sendCodeAction";
    //激活账号
    public static String activeAccount = ROOT + "/user/activeAccount";
    //查询用户所在组成员列表
    public static String queryUserGroup = ROOT + "/user/queryUserGroup";
    //查询产品信息
    public static String queryGoodsAction = ROOT + "/goods/queryGoodsAction";
    //按照商品类型查询商品列表
    public static String queryGoodsListAction = ROOT + "/goods/queryGoodsListAction";
    //供应商新增产品
    public static String addGoodsAction = ROOT + "/goods/addGoodsAction";
    //修改商品信息
    public static String updateGoodsAction = ROOT + "/goods/updateGoodsAction";
    //上下架商品状态
    public static String updateStatusAction = ROOT + "/goods/updateStatusAction";
    //删除商品
    public static String delGoodsAction = ROOT + "/goods/delGoodsAction";
    //查询商品类型
    public static String queryGoodsTypeAction = ROOT + "/goods/queryGoodsTypeAction";
    //查询农产品类型
    public static String queryProductTypeAction = ROOT + "/productorder/queryProductTypeAction";
    //上传商品图片
    public static String uploadGoodsPicAction = ROOT + "/goods/uploadGoodsPicAction";
    //新增商品订单
    public static String addGoodsOrder = ROOT + "/goodsorder/addGoodsOrder";
    //农民买货推送提醒
    public static String buyPublishAction = ROOT + "/publish/buyPublishAction";
    //按类型查询推送信息列表
    public static String queryPublishListAction = ROOT + "/publish/queryPublishListAction";
    //更新推送信息状态
    public static String updatePublishStatusAction = ROOT + "/publish/updatePublishStatusAction";
    //查询用户信息    供应商和农民、小组长都用这个
    public static String queryUserAction = ROOT + "/publish/queryUserAction";
    //询订单列表      供应商和农民、小组长都用这个
    //查询订单信息也用这个接口
    public static String queryGoodsOrderList = ROOT + "/goodsorder/queryGoodsOrderList";
    //查询当前用户的组织结构列表
    public static String queryNodeList = ROOT + "/treenode/queryNodeList";
    //根据父节点查子节点机构列表
    public static String queryNextNodeList = ROOT + "/treenode/queryNextNodeList";
    //取消订单
    public static String delGoodsOrder = ROOT + "/goodsorder/delGoodsOrder";
    //我的预定
    public static String queryProductOrderList = ROOT + "/productorder/queryProductOrderList";
    //新增农产品订单
    public static String addProductOrder = ROOT + "/productorder/addProductOrder";
    //查询交易类型
    public static String querySellTypeAction = ROOT + "/productorder/querySellTypeAction";
    //小组长审批商品订单列表
    public static String queryApprovalGoodsOrderList = ROOT + "/goodsorder/queryApprovalGoodsOrderList";
    //小组长审批商品订单详情
    public static String queryApprovalGoodsOrder = ROOT + "/goodsorder/queryApprovalGoodsOrder";
    //小组长审批农产品订单列表
    public static String queryApprovalProductOrderList = ROOT + "/productorder/queryApprovalProductOrderList";
    //小组长审批农产品订单详情
    public static String queryApprovalProductOrder = ROOT + "/productorder/queryApprovalProductOrder";
    //添加推送提醒
    public static String addPublishAction = ROOT + "/publish/addPublishAction";
    //竞价列表
    public static String queryCompanyOrderList = ROOT + "/productorder/queryCompanyOrderList";
    //批量新增农产品订单
    public static String addProductOrders = ROOT + "/productorder/addProductOrders";
    //查询待竞价的订单列表
    public static String queryQuoteOrderAction = ROOT + "/goodsorder/queryQuoteOrderAction";
    //添加、修改报价
    public static String addUpdateQuoteOrderAction = ROOT + "/goodsorder/addUpdateQuoteOrderAction";
    //删除报价
    public static String delQuoteOrderAction = ROOT + "/goodsorder/delQuoteOrderAction";
}
