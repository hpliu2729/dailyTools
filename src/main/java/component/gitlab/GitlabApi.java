package component.gitlab;

import util.ConfigDriver;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/26
 */
public class GitlabApi {

    private String gitlabHost;

    private String privateToken;

    public GitlabApi(){
        this.gitlabHost = ConfigDriver.getString("gitlab.host");
        this.privateToken = ConfigDriver.getString("gitlab.private.token");
    }



}
