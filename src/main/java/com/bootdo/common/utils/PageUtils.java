package com.bootdo.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bootdo 1992lcg@163.com
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private List<?> rows;
	//当前页
	private int page;
	// 每页条数
	private int limit;
	
	private int totalPage;
	
	private Query params;
	

	public PageUtils(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public PageUtils(List<?> list, int total, Query params) {
		this.rows = list;
		this.total = total;
		this.params = params;
		this.page = Integer.parseInt(params.get("page").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		
		if(this.total%this.limit>0) {
			this.totalPage =  this.total/this.limit+1;
		}else {
			this.totalPage =  this.total/this.limit;
		}		
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public Query getParams() {
		return params;
	}

	public void setParams(Query params) {
		this.params = params;
	}
}
