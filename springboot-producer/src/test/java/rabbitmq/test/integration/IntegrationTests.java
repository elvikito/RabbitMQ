package rabbitmq.test.integration;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class IntegrationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTests.class);
//	private static final String QUEUE_NAME = "queue.test";
	private static final int CALL_ID = 1;
	private static final String TELEPHONE_NUM = "+441690993";
	
	@Autowired
	private Queue queue;
	
	@Autowired
	private TopicExchange exchange;
	
	@Autowired
	private Binding binding;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@Autowired
	private	ConnectionFactory connectionFactory;
	
	private RabbitAdmin rabbitAdmin = null;

	
	@Before
	public void setUpConnectionAndChannelAndExchange() throws IOException {
		
        rabbitAdmin = new RabbitAdmin(connectionFactory); 
        rabbitAdmin.setAutoStartup(true);
        assertNotNull(rabbitAdmin);
        
        rabbitAdmin.declareExchange(exchange);
        assertNotNull(exchange);
        
        rabbitAdmin.declareQueue(queue);
        assertNotNull(queue);
        rabbitAdmin.declareBinding(binding); 
        assertNotNull(binding);
	}
	
	@Test
	public void sendMessage() throws IOException {
		
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        assertNotNull(rabbitTemplate);
        
        Call call = new Call(CALL_ID, TELEPHONE_NUM);
        
        LOGGER.debug("================ Sending Message ================ ");
        LOGGER.debug("Message : " + call);
        LOGGER.debug("================ Message Sent ================ ");
        rabbitTemplate.convertAndSend(AppConfig.QUEUE, call);
        
        receiveMessage();
		
	}
	
	public void receiveMessage() throws IOException {
		
        Call receivedCall = (Call) rabbitTemplate.receiveAndConvert(AppConfig.QUEUE);
        assertNotNull(rabbitTemplate);
        
        LOGGER.debug("================ Receiving Message ================ ");
        LOGGER.debug("Message : " + receivedCall);
        LOGGER.debug("================ Message Received ================ ");
		assertEquals(receivedCall.getId(), CALL_ID);
		assertEquals(receivedCall.telephoneNumber, TELEPHONE_NUM);
	}
	
	@After
	public void destroy() {
		connectionFactory.clearConnectionListeners();
		rabbitAdmin.deleteExchange(AppConfig.EXCHANGE);
		rabbitAdmin.deleteQueue(AppConfig.QUEUE);
	}
	
	public static class Call {
		public long id;
		public String telephoneNumber;
		
		public Call(){}
		public Call(long id, String telephoneNumber) {
			this.id = id;
			this.telephoneNumber = telephoneNumber;
		}
		
		public long getId() {
			return id;
		}
		
		public String getTelephoneNumber() {
			return telephoneNumber;
		}
		
		@Override
		public String toString() {
			return "{id: " + this.id + ", telephoneNumber: " + this.telephoneNumber+"}";
		}
	}
}


