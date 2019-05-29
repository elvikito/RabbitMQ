package rabbitmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private Sender sender;
	
    @RequestMapping("/")
    public String index() {
    	String result = "<h2>Welcome to the Demo!</h2>"
    			+ "<p>To send message to the RabbitMQ click send<p>"
    			+ "<form action='/send'>"
    			+ "<input style='color:blue;font-size:30px;' type='submit' value='Send'></form>";
        return result;
    }
    
    @RequestMapping("/send")
    public String send() {
		Message message = generateData();
		sender.sendToRabbitmq(message);
		String result = "<h2>Success!</h2>"
				+"<a href='/'>Back</a>";
        return result;
    }

	private Message generateData() {
		Call call1 = new Call(1, "+227474");
		Call call2 = new Call(2, "+99161");
		Call call3 = new Call(3, "+99161");
		Call call4 = new Call(4, "+99161");
		Call call5 = new Call(5, "+98161");
		Call call6 = new Call(6, "+98161");
		Call call7 = new Call(7, "+98161");
		Call call8 = new Call(8, "+98161");
		Call call9 = new Call(9, "+98161");
		Call call10 = new Call(10, "+98161");
		Message message = new Message();
		message.list.add(call1);
		message.list.add(call2);
		message.list.add(call3);
		message.list.add(call4);
		message.list.add(call5);
		message.list.add(call6);
		message.list.add(call7);
		message.list.add(call8);
		message.list.add(call9);
		message.list.add(call10);
		return message;
	}

}
