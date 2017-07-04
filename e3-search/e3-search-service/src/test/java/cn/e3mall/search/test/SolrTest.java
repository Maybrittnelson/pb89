package cn.e3mall.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
/*	@Test
	public void test1() throws Exception {
		//第一步把solrj的包加入工程
		//创建solrServer,使用httpsolrserver
		SolrServer solrServer = new HttpSolrServer("http://192.168.242.128:8383/solr");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品"); 
		document.addField("item_price", "199");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void test2() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.242.128:8383/solr");
		solrServer.deleteById("1");
		solrServer.commit();
	}
	
	@Test
	public void test3() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.242.128:8383/solr");
		solrServer.deleteByQuery("item_price:199");
		solrServer.commit();
	}*/
}
