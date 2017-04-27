package com.e7code.common.api.bean;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class PageData<T> implements Serializable {
	private static final long serialVersionUID = -8689661131925501144L;
	
	private long total;			//总条数
	private List<T> rows;		//结果对象
	
	public PageData() {}

	public PageData(long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageData [total=" + total + ", rows=" + rows + "]";
	}
}
