package cn.e3mall.search.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrQueryTest {

/*	@Test
	public void test01() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.242.128:8383/solr");
		//创建查询对象
		SolrQuery query = new SolrQuery();
		//设置过滤条件
		query.setQuery("*:*");
		//solrServer直接查询
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总记录数是"+results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	
	@Test
	public void queryDocumentWithHighlightting() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.242.128:8383/solr");
		//创建查询对象
		SolrQuery query = new SolrQuery();
		//设置查询、过滤条件
		query.setQuery("测试");
		//设置搜索域 DEFAULT_FIELD
		query.set("df", "item_keywords");
		//开启高亮
		query.setHighlight(true);
		//设置高亮字段
		query.addHighlightField("item_title");
		//设置前缀与后缀
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//solrServer直接查询
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总记录数是"+results.getNumFound());
		String itemTitle = null;
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list!=null&&list.size()>0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String)solrDocument.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_price"));
		}
	}*/
}
