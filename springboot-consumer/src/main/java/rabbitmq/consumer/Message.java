package rabbitmq.consumer;

import java.util.Iterator;
import java.util.List;

public class Message {
	public List<Call> list;
	
	public List<Call> getList() {
		return list;
	}
	
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
}

class Call {
	private long id;
	private String telephoneNumber;
	
	
	public Call(){}
	public Call(long id, String telephoneNumer) {
		this.id = id;
		this.telephoneNumber = telephoneNumer;
	}
	public long getId() {
		return id;
	}
	
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public String getCountry() {
		String code = this.getTelephoneNumber().substring(0, 3);
		String country = CountryUtil.getCountry(code);
		return country;
	}
	
	@Override
	public String toString() {
		return "{id: " + this.getId() + ", telephoneNumber: " + this.getTelephoneNumber()+"}";
	}
}
