package util.httpclient;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/26
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * GET
     * @return
     */
    public JSONObject get(String remoteUrl, Header[] headers){
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            HttpGet httpGet = new HttpGet(remoteUrl);
            httpGet.setHeaders(headers);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            result.put("statusCode",statusCode);
            result.put("data", EntityUtils.toString(response.getEntity()));
            response.close();
            httpClient.close();
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }
}
