package cn.e3mall.manager.activemq.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMqTest {
	
/*	@Test
	public void testQueue() throws Exception {
		String brokerURL = "tcp://192.168.242.128:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL );
		Connection connection = connectionFactory.createConnection();
		//打开连接
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//session.createTopic(topicName);
		Queue destination = session.createQueue("test-queue");
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					TextMessage text = (TextMessage)message;
					try {
						String mes = text.getText();
						System.out.println(mes);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		//等待被阅读
		//System.in.read();
		
		//释放资源
		consumer.close();
		session.close();
		connection.close();
	}*/

}
