package component.gitlab;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigDriver;
import util.httpclient.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/26
 */
public class GitlabApi {
    private static final Logger logger = LoggerFactory.getLogger(GitlabApi.class);

    private static final String tokenName = "PRIVATE-TOKEN";

    private String gitlabHost;

    private String privateToken;

    private HttpClientUtil httpClientUtil;

    public GitlabApi() {
        this.gitlabHost = ConfigDriver.getString("gitlab.host");
        this.privateToken = ConfigDriver.getString("gitlab.private.token");
        this.httpClientUtil = new HttpClientUtil();
    }

    /**
     * 列出所有项目
     *
     * @return
     */
    public JSONObject listProject() {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.GET_PROJECTS, gitlabHost, privateToken);
            result = httpClientUtil.get(url, null);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 获取某个项目信息
     *
     * @param projectName
     * @return
     */
    public JSONObject getProject(String projectName) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.GET_PROJECT, gitlabHost, projectName);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            result = httpClientUtil.get(url, headers);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 列出项目所有分支
     *
     * @param projectId
     * @return
     */
    public JSONObject listBranch(String projectId) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.GET_BRANCHES, gitlabHost, projectId);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            result = httpClientUtil.get(url, headers);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 获取某个分支的信息
     *
     * @param projectId
     * @param branchName
     * @return
     */
    public JSONObject getBranch(String projectId, String branchName) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.GET_BRANCH, gitlabHost, projectId, branchName);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            result = httpClientUtil.get(url, headers);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 创建分支
     *
     * @param projectId
     * @param branchName
     * @return
     */
    public JSONObject createBranch(String projectId, String branchName, String sourceBranchName) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.POST_BRANCH, gitlabHost, projectId);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            List<BasicNameValuePair> body = new ArrayList<BasicNameValuePair>();
            body.add(new BasicNameValuePair("branch_name", branchName));
            body.add(new BasicNameValuePair("ref", sourceBranchName));
            result = httpClientUtil.post(url, headers, body);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 删除分支
     *
     * @param projectId
     * @param branchName
     * @return
     */
    public JSONObject deleteBranch(String projectId, String branchName) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.DELETE_BRANCH, gitlabHost, projectId, branchName);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            result = httpClientUtil.delete(url, headers);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 列出所有Tag
     *
     * @param projectId
     * @return
     */
    public JSONObject listTag(String projectId) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.GET_TAG, gitlabHost, projectId);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            result = httpClientUtil.get(url, headers);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }

    /**
     * 创建Tag
     *
     * @param projectId
     * @param tagName
     * @param sourceBranchName
     * @return
     */
    public JSONObject createTag(String projectId, String tagName, String sourceBranchName) {
        JSONObject result = new JSONObject();
        String url;
        try {
            url = getUrl(GitLabUrl.POST_TAG, gitlabHost, projectId);
            Header[] headers = new Header[]{new BasicHeader(tokenName, privateToken)};
            List<BasicNameValuePair> body = new ArrayList<BasicNameValuePair>();
            body.add(new BasicNameValuePair("tag_name", tagName));
            body.add(new BasicNameValuePair("ref", sourceBranchName));
            result = httpClientUtil.post(url, headers, body);
        } catch (Exception e) {
            logger.error("错误:", e);
        }
        return result;
    }


    /**
     * 替换url参数
     *
     * @param originUrl
     * @param params
     * @return
     * @throws Exception
     */
    private String getUrl(String originUrl, String... params) throws Exception {
        int paramIndex = 0;
        String convertUrl = originUrl;
        String regex = "\\{([\\s\\S]*?)\\}";
        Matcher matcher = Pattern.compile(regex).matcher(convertUrl);
        while (matcher.find()) {
            convertUrl = convertUrl.replace("{" + matcher.group(1) + "}", params[paramIndex]);
            paramIndex++;
        }
        return convertUrl;
    }


}
