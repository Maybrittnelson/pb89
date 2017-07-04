package cn.e3mall.manager.activemq.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;



public class ActiveMqTest {
	
/*	@Test
	public void testQueueProducer() throws Exception {
		// 第一步：创建ConnectionFactory
		String brokerURL = "tcp://192.168.242.128:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		// 第二步：通过工厂，创建Connection
		Connection connection = connectionFactory.createConnection();
		// 第三步：连接启动
		connection.start();
		// 第四步：通过连接获取session会话
		// 第一个参数：是否启用ActiveMQ的事务，如果为true，第二个参数失效。
		// 第二个参数：应答模式,AUTO_ACKNOWLEDGE为自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：通过session创建destination，两种方式：Queue、Topic
		// 参数：消息队列的名称，在后台管理系统中可以看到
		//session.createTopic(topicName);
		Queue queue = session.createQueue("test-queue");
		// 第六步：通过session创建MessageProducer
		MessageProducer producer = session.createProducer(queue);
		// 第七步：创建Message
		// 方式1：
		TextMessage message = new ActiveMQTextMessage();
		message.setText("设置点对点消息");
		// 方式2：
		// TextMessage message = session.createTextMessage("设置点对点消息");
		// 第八步：通过producer发送消息
		producer.send(message);
		// 第九步：关闭资源
		producer.close();
		session.close();
		connection.close();
	}
*/

}
