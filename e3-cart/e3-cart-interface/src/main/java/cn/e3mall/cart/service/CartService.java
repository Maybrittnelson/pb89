package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.manager.po.ext.Cart;

/**
 * 购物车业务层
 *		
 */
public interface CartService {

	/**
	 * 根据userId
	 * 查询redis中指定的CartList
	 * @param userId
	 * @return
	 */
	List<Cart> getCartListFromRedis(Long userId);
	
	/**
	 * 添加指定的item到redisCartList中
	 * @param userId
	 * @param itemId
	 * @param num
	 */
	void addItem2CartListFromJedis(Long userId, Long itemId, Integer num);
	
	/**
	 * 更新redisCartList中指定的item的num
	 * @param userId
	 * @param itemId
	 * @param num
	 */
	void updateItemNumFromRedisCartList(Long userId, Long itemId, Integer num);
	
	/**
	 * 删除redisCartList中指定的item
	 * @param userId
	 * @param itemId
	 */
	void deleteItemFromRedisCartList(Long userId, Long itemId);

	/**
	 * 合并cookie上的cartList到指定的redisCartList上
	 * @param userId
	 * @param jsonToList
	 */
	void mergeCartListFromCookie2Redis(Long userId, List<Cart> jsonToList);

}
