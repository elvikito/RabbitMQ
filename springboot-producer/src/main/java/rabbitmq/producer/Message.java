package rabbitmq.producer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Message {
	public List<Call> list = new ArrayList<Call>();
	
	@Override
	public String toString() {
        String result = "[";
        Iterator<Call> iter = this.list.iterator();
        while(iter.hasNext()) {
        	Call call = iter.next();
            result += call.toString();
            if(iter.hasNext()) {
            	result += ",";
            }
        }
        result+="]";
        return result;
    }
	
	public List<Call> getList() {
		return list;
	}
	
	public static void generateData(Message message) {
		Call call1 = new Call(1, "+4187900087");
		Call call2 = new Call(2, "+4474744523");
		Call call3 = new Call(3, "+4416100345");
		Call call4 = new Call(4, "+4916142345");
		Call call5 = new Call(5, "+4916121233");
		Call call6 = new Call(6, "+4187900087");
		Call call7 = new Call(7, "+4416144523");
		Call call8 = new Call(8, "+4187900087");
		Call call9 = new Call(9, "+4187900087");
		Call call10 = new Call(10, "+4987900087");
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
	}


}

class Call {
	public long id;
	public String telephoneNumber;
	
	public Call(){}
	public Call(long id, String telephoneNumber) {
		this.id = id;
		this.telephoneNumber = telephoneNumber;
	}
	
	@Override
	public String toString() {
		return "{id: " + this.id + ", telephoneNumber: " + this.telephoneNumber+"}";
	}
}
