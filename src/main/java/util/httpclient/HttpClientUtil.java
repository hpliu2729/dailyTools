package util.httpclient;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/26
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String STATUS_CODE = "statusCode";
    private static final String DATA = "data";

    /**
     * GET
     *
     * @return
     */
    public JSONObject get(String remoteUrl, Header[] headers) {
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(remoteUrl);
            if (null != headers)
                httpGet.setHeaders(headers);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            result.put(STATUS_CODE, statusCode);
            result.put(DATA, EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            logger.error("错误:", e);
            return createErrorResult(e);
        } finally {
            closeHttpClient(response, httpClient);
        }
        return result;
    }

    /**
     * POST
     * @param remoteUrl
     * @param headers
     * @param body
     * @return
     */
    public JSONObject post(String remoteUrl, Header[] headers, List<BasicNameValuePair> body) {
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(remoteUrl);
            if (null != body)
                httpPost.setEntity(new UrlEncodedFormEntity(body));
            if(null != headers)
                httpPost.setHeaders(headers);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            result.put(STATUS_CODE, statusCode);
            result.put(DATA, EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            logger.error("错误:", e);
            return createErrorResult(e);
        } finally {
            closeHttpClient(response, httpClient);
        }
        return result;
    }

    /**
     * PUT
     * @param remoteUrl
     * @param headers
     * @param body
     * @return
     */
    public JSONObject put(String remoteUrl, Header[] headers, List<BasicNameValuePair> body) {
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPut httpPut = new HttpPut(remoteUrl);
            if (null != body)
                httpPut.setEntity(new UrlEncodedFormEntity(body));
            if(null != headers)
                httpPut.setHeaders(headers);
            response = httpClient.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();
            result.put(STATUS_CODE, statusCode);
            result.put(DATA, EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            logger.error("错误:", e);
            return createErrorResult(e);
        } finally {
            closeHttpClient(response, httpClient);
        }
        return result;
    }

    /**
     * DELETE
     * @param remoteUrl
     * @param headers
     * @return
     */
    public JSONObject delete(String remoteUrl, Header[] headers) {
        JSONObject result = new JSONObject();
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpDelete httpDelete = new HttpDelete(remoteUrl);
            if(null != headers)
                httpDelete.setHeaders(headers);
            response = httpClient.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            result.put(STATUS_CODE, statusCode);
            result.put(DATA, EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            logger.error("错误:", e);
            return createErrorResult(e);
        } finally {
            closeHttpClient(response, httpClient);
        }
        return result;
    }

    /**
     * 关闭客户端
     * @param response
     * @param client
     */
    private void closeHttpClient(CloseableHttpResponse response, CloseableHttpClient client) {
        try {
            response.close();
            client.close();
        } catch (Exception e) {
            logger.error("Close httpclient error:", e);
        }

    }

    /**
     * 返回错误
     * @param e
     * @return
     */
    private JSONObject createErrorResult(Exception e) {
        JSONObject errorResult = new JSONObject();
        errorResult.put(STATUS_CODE, 500);
        errorResult.put(DATA, e.toString());
        return errorResult;
    }
}
