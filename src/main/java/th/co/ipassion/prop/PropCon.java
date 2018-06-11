package th.co.ipassion.prop;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import th.co.ipassion.main.MasicApplication;

public class PropCon {
	public static String get(String key) {
		Properties prop = new Properties();
		try {
			prop.load( new InputStreamReader(MasicApplication.class.getClassLoader().getResourceAsStream("config.properties")));
			return prop.getProperty(prop.getProperty("run_on")+"."+key);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
