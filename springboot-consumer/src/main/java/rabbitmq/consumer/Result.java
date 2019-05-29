package rabbitmq.consumer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
	private static final Logger LOGGER = LoggerFactory.getLogger(Result.class);
	
	private Map<String, Long> calls;
	private int totalCalls;
	
	
	public static Result createResultFromCalls(List<Call> calls) {
		Result result = new Result();
		result.totalCalls = calls.size();
		
		Map<String, Long> callsMap = calls.stream().collect(
				Collectors.groupingBy(call -> call.getCountry(), Collectors.counting()));

		result.calls = callsMap;
		return result;
	}
	
	public void printReport() {
		LOGGER.info("================ Report Start ================ ");
		LOGGER.info("Total calls received: " + this.totalCalls);
		
		Map<String, Long> sortedMap = this.calls.entrySet().stream().sorted(
				Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		
		for(Map.Entry<String, Long> entry: sortedMap.entrySet()) {
			LOGGER.info("No of calls from " + entry.getKey() + " are " + entry.getValue());
		}
		LOGGER.info("================ Report End ================ ");
	}


	
	
	public static Result createFromMessage(Message message) {
		Result result = new Result();
		Map<String, Integer> calls = new HashMap<String, Integer>();
		result.totalCalls = message.getList().size();
		for(Call call: message.getList()) {
			String code = call.getTelephoneNumber().substring(0, 3);
			String country = CountryUtil.getCountry(code);
			if(calls.containsKey(country)) {
				Integer value = calls.get(country);
				value++;
				calls.put(country, value);
			} else {
				calls.put(country, 1);
			}
		}
//		result.calls = calls;
		return result;
	}

}
