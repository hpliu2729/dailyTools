package util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigTest {
	Logger logger = LoggerFactory.getLogger(ConfigTest.class);
	@Test
	public void config(){
		logger.info("LDAP.MANAGER_DN:" + ConfigDriver.getString("LDAP.MANAGER_DN"));
		InputStream in = ClassLoader.getSystemResourceAsStream("config.properties");
		Properties p = new Properties();

		try {
			p.load(in);
			logger.info("LDAP.MANAGER_DN:" + p.getProperty("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("host:" + properties.get("ftp.host"));

	}

}
