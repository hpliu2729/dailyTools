package util.httpclient;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigDriver;

/**
 * Created by xuyexin on 16/3/2.
 */
public class HttpClientTest {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	@Test
	public void testGet(){
	}

}
