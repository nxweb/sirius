package com.bcyj99.sirius.core.common.page;

public class PagedParamVo {
    private Integer pageNo;//当前第几页
    private Integer pageSize;//每页显示记录数
    
    private Integer page;//当前第几页-easyui专用
    private Integer rows;//每页显示记录数-easyui专用
    
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
