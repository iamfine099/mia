/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bootdo.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bootdo.common.config.ApplicationContextRegister;
import com.bootdo.common.dao.DictDao;
import com.bootdo.common.domain.DictDO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = ApplicationContextRegister.getBean(DictDao.class);
	
	public static final String CACHE_DICT_MAP = "dictMap";

	/**
	 * 根据字段类型、字典值获取对应的名称
	 * @param type 类型
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (DictDO dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}
	/**
	 * 根据字段类型、字典值获取对应的名称(多个值逗号隔开)
	 * @param type 类型
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}
	/**
	 * 根据字段类型、字典名称获取对应的值
	 * @param type 类型
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (DictDO dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getName())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	/**
	 * 根据类型获取数据字段list集合
	 * @param type
	 * @return
	 */
	public static List<DictDO> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<DictDO>> dictMap = (Map<String, List<DictDO>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (DictDO dict : dictDao.listSortDesc(new HashMap<String,Object>())){
				List<DictDO> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<DictDO> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
}
