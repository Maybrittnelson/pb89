package cn.e3mall.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SolrCloudTest {
/*	
	@Test
	public void test1() throws Exception {
		// solrcloud集群使用的zookeeper地址
		String zkHost = "192.168.242.128:2281,192.168.242.128:2282,192.168.242.128:2283";
		// 默认集群名称，我的solr服务器的collection名称是collection2_shard1_replica1
		// 默认集群名称就是去掉“_shard1_replica1”的名称
		String defaultCollection = "collection2";

		// 构造CloudSolrServer对象
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		// 设置默认集群名称
		solrServer.setDefaultCollection(defaultCollection);

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
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
		CloudSolrServer solrServer = (CloudSolrServer) ctx.getBean(CloudSolrServer.class);
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品"); 
		document.addField("item_price", "199");
		solrServer.add(document);
		solrServer.commit();
	}
*/
}
