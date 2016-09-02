package component.gitlab;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/29
 */
public class GitLabUrl {
    /**
     * 获取所有项目
     */
    public static final String GET_PROJECTS = "{gitlabHost}/api/v3/projects";
    /**
     * 获取某个项目
     */
    public static final String GET_PROJECT = "{gitlabHost}/api/v3/projects?name={projectName}";
    /**
     * 获取所有分支
     */
    public static final String GET_BRANCHES = "{gitlabHost}/api/v3/projects/{projectId}/repository/branches";
    /**
     * 获取某个分支
     */
    public static final String GET_BRANCH = "{gitlabHost}/api/v3/projects/{projectId}/repository/branches/{branchName}";
    /**
     * 创建分支
     */
    public static final String POST_BRANCH = "{gitlabHost}/api/v3/projects/{projectId}/repository/branches";
    /**
     * 删除分支
     */
    public static final String DELETE_BRANCH = "{gitlabHost}/api/v3/projects/{projectId}/repository/branches/{branchName}";
    /**
     * 获取所有tag
     */
    public static final String GET_TAG = "{gitlabHost}/api/v3/projects/{projectId}/repository/tags";
    /**
     * 创建tag
     */
    public static final String POST_TAG = "{gitlabHost}/api/v3/projects/{projectId}/repository/tags";
}
