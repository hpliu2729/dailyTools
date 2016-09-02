package component.gitlab;

import net.sf.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/26
 */
public class GitlabApiTest {
    private static final Logger logger = LoggerFactory.getLogger(GitlabApiTest.class);

    private GitlabApi gitlabApi;

    public GitlabApiTest(){
        this.gitlabApi = new GitlabApi();
    }

    @Test
    @Ignore
    public void testListProject(){
        JSONObject jsonObject = gitlabApi.listProject();
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testGetProject(){
        JSONObject jsonObject = gitlabApi.getProject("testChaos");
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testListBranch(){
        JSONObject jsonObject = gitlabApi.listBranch("2");
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testGetBranch(){
        JSONObject jsonObject = gitlabApi.getBranch("2", "master");
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testCreatesBranch(){
        JSONObject jsonObject = gitlabApi.createBranch("2", "abc", "master");
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testDeleteBranch(){
        JSONObject jsonObject = gitlabApi.deleteBranch("2", "abc");
        logger.info(jsonObject.toString());
    }

    @Test
    @Ignore
    public void testListTag(){
        JSONObject jsonObject = gitlabApi.listTag("2");
        logger.info(jsonObject.toString());
    }

    @Test
    public void createTag(){
        JSONObject jsonObject = gitlabApi.createTag("2", "tag0012", "master");
        logger.info(jsonObject.toString());
    }

}
