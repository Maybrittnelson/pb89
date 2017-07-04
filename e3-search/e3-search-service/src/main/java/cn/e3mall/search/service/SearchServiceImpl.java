package cn.e3mall.search.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.SearchResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.mapper.search.ItemMapper;
import cn.e3mall.search.dao.SearchDao;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao dao;
	
	@Value("${DEFAULT_FIELD}")
	private String DEFAULT_FIELD;
	
	@Override
	public SearchResult getSearchResults(Integer page, Integer pageRows, String keyword) throws Exception {
		SolrQuery query = new SolrQuery();
		//设置查询条件
		if(keyword!=null && !"".equals(keyword)){
			query.setQuery(keyword);
		}else{
			//查询所有
			query.setQuery("*:*");
		}
		//设置搜索域
		query.set("df", DEFAULT_FIELD);
		
		//设置分页
		query.setStart((page-1)*pageRows);
		query.setRows(pageRows);
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//计算总页数
		SearchResult searchResults = dao.getSearchResults(query );
		
		Integer recourdCount = searchResults.getRecourdCount();
		int totalPages = recourdCount/pageRows;
		if(recourdCount%pageRows>0)
		totalPages++;
		searchResults.setTotalPages(totalPages);
		
		return searchResults;
	}


	
}
