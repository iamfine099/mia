package com.bootdo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * UUID 32
 * @author Administrator
 *
 */
public class UUIDUtils {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public static void main(String[] args) {
		System.out.println(UUIDUtils.getUUID());
	}

	//获取订单编号
	public static String getOrderSn() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date())+StringUtils.getRandomStringByLength(4);
	}
}
