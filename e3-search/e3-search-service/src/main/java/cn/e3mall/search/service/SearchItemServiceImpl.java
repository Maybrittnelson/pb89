package cn.e3mall.search.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.mapper.search.ItemMapper;

@Service
public class SearchItemServiceImpl implements SearchItemService {
	
	@Autowired
	private ItemMapper mapper;
	
	@Resource
	private SolrServer solrServer;

	@Override
	public E3Result importItems() {
		try{
			List<SearchItem> itemList = mapper.getItemList();
			
			List<SolrInputDocument> documents = new ArrayList<>();//创建文档集合对象
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_category_name", searchItem.getCategory_name());
				//document.addField("item_description", searchItem.getDesc());
				document.addField("item_sell_point", searchItem.getSell_point());
				
				String imagePath = searchItem.getImage();
				if(StringUtils.isNotBlank(imagePath)&&imagePath.contains(",")) {
					imagePath = imagePath.split(",")[0];
				}
				document.addField("item_image", imagePath);
				
				
				documents.add(document);
			}	
			
			solrServer.add(documents);//添加文档集合对象
			solrServer.commit();
			return E3Result.ok();
		} catch(Exception ex) {
			ex.printStackTrace();
			return E3Result.build(500, "商品导入失败");
		}
	}

	@Override
	public E3Result addDocument(Long itemId) throws Exception {
		Thread.sleep(2000);
		SearchItem searchItem = mapper.getItemById(itemId);
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_category_name", searchItem.getCategory_name());
		//document.addField("item_description", searchItem.getDesc());//solrcloud之前seachme.xml未加description域
		document.addField("item_sell_point", searchItem.getSell_point());
		
		String imagePath = searchItem.getImage();
		if(StringUtils.isNotBlank(imagePath)&&imagePath.contains(",")) {
			imagePath = imagePath.split(",")[0];
		}
		document.addField("item_image", imagePath);
		
		solrServer.add(document);
		solrServer.commit();
		return E3Result.ok();
	}

}
