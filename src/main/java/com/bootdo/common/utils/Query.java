package com.bootdo.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 
	private int offset;
	// 每页条数
	private int limit;
	//当前页
	private int page;
	

	public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		if( null !=params.get("limit")) {
			this.limit = Integer.parseInt(params.get("limit").toString());
		}else {
			this.limit = 10;
		}
		
		if(null != params.get("offset") ) {
			this.offset = Integer.parseInt(params.get("offset").toString());
			this.page =  offset / limit + 1 ;
		}else if(null != params.get("page")){
			this.page =  Integer.parseInt(params.get("page").toString());
			this.offset = (this.page-1)*limit;
		}else  {//第一次加载
			this.offset = 0;
			this.page = 1;
		}
		
		this.put("offset", offset);
		this.put("page", offset / limit + 1);
		this.put("limit", limit);
		System.out.println("offset=="+offset);
		System.out.println("limit=="+limit);
	}

	
	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
