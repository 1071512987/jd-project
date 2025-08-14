package com.hzau.college.pojo.vo.list;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo {
    //订单自增id
    private Integer id;
    //订单号，唯一
    private String orderId;
    //买家id		
    private String buyerId;
    //商品编号
    private String productId;
    //商品名称
    private String productName;
    //交易状态,0为进行中，1为已完成，2为取消交易，3为异常
    private Short tradeStatus;
    //支付状态，0未付款，1为线上微信已付款，2为线上支付宝已付款
    private Short payStatus;
    //购买数量
    private Integer orderNum;
    //订单原金额（未打折）
    private BigDecimal originAmount;
    //付款金额（真实金额）
    private BigDecimal totalAmount;
    //订单创建时间
    private Date createTime;
    //外部订单号，如支付宝平台订单号
    private String outTradeNo;
    //订单支付时间
    private Date payTime;
    //订单附言，用户填写
    private String remark;
}

