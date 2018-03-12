package com.nylc.nylc.utils;

/**
 * 请求接口地址
 * Created by 吴曰阳 on 2017/12/9.
 */

public class Urls {
    private static String ROOT = "http://47.98.51.252:8081/nylc";
    public static String LOGIN=ROOT+"/user/loginAction";
    public static String REGISTER=ROOT+"/user/registerAction";
    //登录用户
    public static String loginAction=ROOT+"/user/loginAction";
    //退出登录
    public static String logOutAction=ROOT+"/user/logOutAction";
    //忘记密码
    public static String forgetPassAction=ROOT+"/user/forgetPassAction";
    //修改密码
    public static String modifyPassAction=ROOT+"/user/modifyPassAction";
    //发送验证码
    public static String sendCodeAction = ROOT +"/auth/sendCodeAction";
    //激活账号
    public static String activeAccount = ROOT + "/user/activeAccount";
    //查询用户所在组成员列表
    public static String queryUserGroup = ROOT + "/user/queryUserGroup";
    //查询产品信息
    public static String queryGoodsAction = ROOT + "/goods/queryGoodsAction";
    //按照商品类型查询商品列表
    public static String queryGoodsListAction = ROOT+"/goods/queryGoodsListAction";
    //供应商新增产品
    public static String addGoodsAction = ROOT+"/goods/addGoodsAction";
    //修改商品信息
    public static String updateGoodsAction = ROOT+"/goods/updateGoodsAction";
    //上下架商品状态
    public static String updateStatusAction = ROOT+"/goods/updateStatusAction";
    //删除商品
    public static String delGoodsAction = ROOT+"/goods/delGoodsAction";
    //查询商品类型
    public static String queryGoodsTypeAction = ROOT+"/goods/queryGoodsTypeAction";
    //上传商品图片
    public static String uploadGoodsPicAction = ROOT+"/goods/uploadGoodsPicAction";
    //新增商品订单
    public static String addGoodsOrder = ROOT+"/goodsorder/addGoodsOrder";
    //农民买货推送提醒
    public static String buyPublishAction = ROOT+"/publish/buyPublishAction";
    //按类型查询推送信息列表
    public static String queryPublishListAction = ROOT+"/publish/queryPublishListAction";
    //更新推送信息状态
    public static String updatePublishStatusAction = ROOT+"/publish/updatePublishStatusAction";
//    //
//    public static String  = ROOT+"";


}
