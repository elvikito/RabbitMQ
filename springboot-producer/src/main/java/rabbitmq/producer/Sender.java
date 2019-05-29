package rabbitmq.producer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class Sender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;
	
	@Autowired
	private MappingJackson2MessageConverter mappingJackson2MessageConverter;

	public void sendToRabbitmq(final Message message) {

		this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);

//		LOGGER.info("Sending message to call-exchange of queue.call " + message);

//		this.rabbitMessagingTemplate.convertAndSend("call-exchange", "queue.call", message);
		
		for(Call call: message.getList()) {
			
			LOGGER.info("Sending message to call-exchange of queue.call " + call);
			this.rabbitMessagingTemplate.convertAndSend("call-exchange", "queue.call", call);
			
		}
	}
}