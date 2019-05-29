package rabbitmq.consumer;

import java.util.HashMap;
import java.util.Map;

public class CountryUtil {
	private static Map<String, String> map = new HashMap<>();

	static {
	    map.put("+49", "Germany");
	    map.put("+44", "United Kingdom");
	    map.put("+41", "Switzerland");
	    map.put("+98", "Turkey");
	    map.put("+22", "Gibralter");
	    map.put("+99", "Iran");
	}

	public static String getCountry(String code) {
	    String country = map.get(code);
	    if (country == null) {
	        throw new IllegalArgumentException("Unknown country code " + code);
	    }
	    return country;
	}

}
