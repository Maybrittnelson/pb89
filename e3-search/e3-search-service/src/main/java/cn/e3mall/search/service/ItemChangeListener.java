package cn.e3mall.search.service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchItemService searchItemServiceImpl;
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			TextMessage text = (TextMessage)message;
			try {
				long itemId = Long.parseLong(text.getText());
				searchItemServiceImpl.addDocument(itemId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
