package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.ext.OrderInfo;

public interface OrderService {
	
	E3Result creatOrder(OrderInfo orderInfo);
}
