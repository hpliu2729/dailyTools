package util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xuyexin on 16/3/2.
 */
public class HttpClientTest {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	@Test
	public void testHttpClient() {
		String remoteUrl = "http://jenkins.oneplus.org:8080/job/chaos/235/";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = null;
			HttpGet httpGet = new HttpGet(remoteUrl);
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("状态码：" + statusCode);
			//405暂时也允许通过
			if (statusCode != 200 && statusCode != 405) {
			}
			response.close();
			httpClient.close();
		} catch (Exception e) {
			logger.error("错误:", e);
		}
	}

}
