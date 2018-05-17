package com.nylc.nylc.utils;

/**
 * 请求接口地址
 * Created by 吴曰阳 on 2017/12/9.
 */

public class Urls {
    //    private static String ROOT = "http://47.98.51.252:8081/nylc";
    private static String ROOT = "http://47.98.51.252:54322/nylc";

    //    public static String IMG = "http://47.98.51.252:8081/nylcpic/";
    public static String IMG = "http://47.98.51.252:54322/nylcpic/";
    public static String REGISTER = ROOT + "/user/registerAction";
    //1.1 登录用户
    public static String loginAction = ROOT + "/user/loginAction";
    //1.2 退出登录
    public static String logOutAction = ROOT + "/user/logOutAction";
    //1.3 忘记密码
    public static String forgetPassAction = ROOT + "/user/forgetPassAction";
    //1.4 修改密码
    public static String modifyPassAction = ROOT + "/user/modifyPassAction";
    //1.5 发送验证码
    public static String sendCodeAction = ROOT + "/auth/sendCodeAction";
    //1.6 激活账号
    public static String activeAccount = ROOT + "/user/activeAccount";
    //1.8 查询用户所在组成员列表
    public static String queryUserGroup = ROOT + "/user/queryUserGroup";
    //1.9 查询产品信息
    public static String queryGoodsAction = ROOT + "/goods/queryGoodsAction";
    //1.10 按照商品类型查询商品列表
    public static String queryGoodsListAction = ROOT + "/goods/queryGoodsListAction";
    //1.11 供应商新增产品
    public static String addGoodsAction = ROOT + "/goods/addGoodsAction";
    //1.12 修改商品信息
    public static String updateGoodsAction = ROOT + "/goods/updateGoodsAction";
    //1.13 上下架商品状态
    public static String updateStatusAction = ROOT + "/goods/updateStatusAction";
    //1.14 删除商品
    public static String delGoodsAction = ROOT + "/goods/delGoodsAction";
    //1.15 查询商品类型
    public static String queryGoodsTypeAction = ROOT + "/goods/queryGoodsTypeAction";
    //1.16 上传商品图片
    public static String uploadGoodsPicAction = ROOT + "/goods/uploadGoodsPicAction";
    //1.17 新增商品订单
    public static String addGoodsOrder = ROOT + "/goodsorder/addGoodsOrder";
    //1.18 添加推送提醒
    public static String addPublishAction = ROOT + "/publish/addPublishAction";
    //1.19 按类型查询推送信息列表
    public static String queryPublishListAction = ROOT + "/publish/queryPublishListAction";
    //1.20 更新推送信息状态
    public static String updatePublishStatusAction = ROOT + "/publish/updatePublishStatusAction";
    //1.21 查询用户信息    供应商和农民、小组长都用这个
    public static String queryUserAction = ROOT + "/user/queryUserAction";
    //1.22询订单列表      供应商和农民、小组长都用这个,查询订单信息也用这个接口
    public static String queryGoodsOrderList = ROOT + "/goodsorder/queryGoodsOrderList";
    //1.24 查询当前用户的组织结构列表
    public static String queryNodeList = ROOT + "/treenode/queryNodeList";
    //1.25 根据父节点查子节点机构列表
    public static String queryNextNodeList = ROOT + "/treenode/queryNextNodeList";
    //1.26 取消订单
    public static String delGoodsOrder = ROOT + "/goodsorder/delGoodsOrder";
    //1.27 查询待竞价的订单列表
    public static String queryQuoteOrderAction = ROOT + "/goodsorder/queryQuoteOrderAction";
    //1.28 添加、修改报价
    public static String addUpdateQuoteOrderAction = ROOT + "/goodsorder/addUpdateQuoteOrderAction";
    //1.29 删除报价
    public static String delQuoteOrderAction = ROOT + "/goodsorder/delQuoteOrderAction";
    //1.30 供应商操作订单（发货、交易完成）(完成)
    public static String operateQuoteOrderAction = ROOT + "/goodsorder/operateQuoteOrderAction";
    //1.31 新增农产品订单
    public static String addProductOrder = ROOT + "/productorder/addProductOrder";
    //1.32 查询农产品类型
    public static String queryProductTypeAction = ROOT + "/productorder/queryProductTypeAction";
    //1.33 查询交易类型
    public static String querySellTypeAction = ROOT + "/productorder/querySellTypeAction";
    //1.34 批量新增农产品订单
    public static String addProductOrders = ROOT + "/productorder/addProductOrders";
    //1.35 我的预定
    public static String queryProductOrderList = ROOT + "/productorder/queryProductOrderList";
    //1.36 小组长审批商品订单列表
    public static String queryApprovalGoodsOrderList = ROOT + "/goodsorder/queryApprovalGoodsOrderList";
    //1.37 小组长审批商品订单详情
    public static String queryApprovalGoodsOrder = ROOT + "/goodsorder/queryApprovalGoodsOrder";
    //1.38 小组长审批农产品订单列表
    public static String queryApprovalProductOrderList = ROOT + "/productorder/queryApprovalProductOrderList";
    //1.39 小组长审批农产品订单详情
    public static String queryApprovalProductOrder = ROOT + "/productorder/queryApprovalProductOrder";
    //1.40 小组长审批编辑农产品订单
    public static String updateProductOrder = ROOT + "/productorder/updateProductOrder";
    //1.41 小组长审批删除农产品订单
    public static String delProductOrder = ROOT + "/productorder/delProductOrder";
    //1.42 小组长完成交易
    public static String commitProductOrder = ROOT + "/productorder/commitProductOrder";
    //1.43 农民取消农产品订单
    public static String invalidProductOrder = ROOT + "/productorder/invalidProductOrder";
    //1.44 查询时间节点
    public static String queryTimeNodeAction = ROOT + "/productorder/queryTimeNodeAction";
    //1.45 查询企业竞价列表
    public static String queryCompanyOrderList = ROOT + "/productorder/queryCompanyOrderList";
    //1.46 农产品企业报价列表
    public static String queryQuoteOrderList = ROOT + "/productorder/queryQuoteOrderList";
    //1.47 农产品报价
    public static String addQuoteAction = ROOT + "/productorder/addQuoteAction";

}
