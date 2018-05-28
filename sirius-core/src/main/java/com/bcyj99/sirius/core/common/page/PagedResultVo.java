package com.bcyj99.sirius.core.common.page;

import java.util.List;

public class PagedResultVo<E> {
    private Long total;//数据总量
    private List<E> rows;//当前页数据
    
	public PagedResultVo() {
		super();
	}

	public PagedResultVo(Long total, List<E> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}
}
