package rabbitmq.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	private List<Call> calls = new ArrayList<Call>();
	
	@RabbitListener(queues = "queue.call")
	public void receiveMessage(Call call) {
		LOGGER.info("Received Message " + call);
		calls.add(call);
		
		if(calls.size() == 10) {
			Result result = Result.createResultFromCalls(calls);
			result.printReport();
			calls.clear();
		}
	}

	
/*	
	@RabbitListener(queues = "queue.call")
	public void receiveMessage(Message message) {
		LOGGER.info("Received Message " + message.toString());
		Result result = Result.createFromMessage(message);
		result.print();
	}
*/
}
