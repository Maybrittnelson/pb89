package cn.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.Item;
import cn.e3mall.manager.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 对新增商品生成html
 *
 */
public class ItemMessageListener implements MessageListener {
	
	@Autowired
	private FreeMarkerConfigurer configurer;
	
	@Autowired
	private ItemService service;
	
	@Value("${ITEM_TEMPLATE_NAME}")
	private String ITEM_TEMPLATE_NAME;
	
	@Value("${ITEM_HTML_PATH_PRE}")
	private String ITEM_HTML_PATH_PRE;
	
	@Value("${ITEM_HTML_PATH_EXT}")
	private String ITEM_HTML_PATH_EXT;
	
	@Override
	public void onMessage(Message message) {
		FileWriter out = null;
		try {
			//模板
			Configuration configuration = configurer.getConfiguration();
			Template template = configuration.getTemplate(ITEM_TEMPLATE_NAME);
			
			//数据
			Long itemId = 0L;
			if(message instanceof TextMessage) {
				itemId = Long.parseLong(((TextMessage) message).getText());
			}
			TbItem tbItem = service.queryItemById(itemId);
			Item item = new Item(tbItem);
			Map<String, Object> map = new HashMap<>();
			map.put("item", item);
			
			//输出
			String filePath = ITEM_HTML_PATH_PRE+itemId+ITEM_HTML_PATH_EXT;
			out = new FileWriter(new File(filePath));
			
			template.process(map, out);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	


}
