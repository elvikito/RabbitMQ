package rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Bean
	Queue queue() {
		return new Queue("queue.call", false);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("call-exchange");
	}

	@Bean
	Binding bindingExchange(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("queue.call");
	}
	
	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}

	@Autowired
	private Sender sender;

	@Override
	public void run(String... args) throws Exception {
		Message message = new Message();
		Message.generateData(message);
		sender.sendToRabbitmq(message);
	}
}



