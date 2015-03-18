package com.miaxis.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * 分页工具
 * 
 */
public class PageConfig {
	/**
	 * 每页记录数
	 * 
	 * @param pageSize
	 */
	private Integer pageSize = 10;
	/**
	 * 页数
	 */
	private Integer pages;
	/**
	 * 当页码
	 * 
	 * @param curPageNO
	 */
	private Integer curPageNO = 1;
	/**
	 * 总记录数
	 * 
	 * @param totalCount
	 */
	private Integer totalCount = null;
	/**
	 * 是否分页
	 */
	private boolean isPaging = true;

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public Integer getCurPageNO() {
		return curPageNO;
	}

	public void setCurPageNO(Integer curPageNO) {
		this.curPageNO = curPageNO;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	/**
	 * 计算总页数
	 */
	public void calculatePages(int counts) {
		if (counts == 0) {
			this.setPages(0);
		} else if (counts <= pageSize) {
			this.setPages(1);
		} else if (counts % pageSize != 0) {
			this.setPages(counts / pageSize + 1);
		} else {
			this.setPages(counts / pageSize);
		}
	}

	/**
	 * 初始化分页对象数据
	 */
	public void initData(String curPageNO,String pageSize, boolean isPaging) {
		this.setCurPageNO(StringUtils.isEmpty(curPageNO) ? 1 : Integer.parseInt(curPageNO));
		this.setPageSize(StringUtils.isEmpty(pageSize) ? this.getPageSize(): Integer.parseInt(pageSize));
		this.setPaging(isPaging);
	}
}
