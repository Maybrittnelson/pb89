package cn.e3mall.common.po;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类
 * 使用于：业务层
 * @author DELL
 *
 */
public class DatagridResult implements Serializable{
	
	private Long total;
	private List rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
