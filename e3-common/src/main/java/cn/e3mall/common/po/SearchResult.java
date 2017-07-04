package cn.e3mall.common.po;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	
	private List<SearchItem> itemList;
	private Integer totalPages;
	private Integer recourdCount;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(Integer recourdCount) {
		this.recourdCount = recourdCount;
	}
	
	
}	
