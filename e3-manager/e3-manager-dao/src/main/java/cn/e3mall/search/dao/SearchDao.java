package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.po.SearchItem;
import cn.e3mall.common.po.SearchResult;

/**
 * 索引库持久层
 *
 */
@Repository
public class SearchDao {
	
	@Resource
	private SolrServer solrServer;
	
	public SearchResult getSearchResults(SolrQuery query) throws Exception {
		//查询索引库
		QueryResponse response = solrServer.query(query);
		//取出查询结果
		SolrDocumentList documentList = response.getResults();
		
		//设置查询结果
		SearchResult results = new SearchResult();
		results.setRecourdCount((int) documentList.getNumFound());
		
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : documentList) {
			//添加查询商品信息
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			
			//取出高亮的结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String item_title;
			if(list!=null&&list.size()>0) {
				item_title = list.get(0);
			} else {
				item_title = (String)solrDocument.get("item_title");
			}
			
			searchItem.setTitle(item_title);
			itemList.add(searchItem);
		}
		results.setItemList(itemList);
		return results;
	}
}
