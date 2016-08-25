package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/12/3
 */
public class FileUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilTest.class);

    @Test
    @Ignore
    public void testCreateDir(){
        logger.info(System.getProperty("java.io.tmpdir"));
        logger.info(FileUtil.createDir(System.getProperty("java.io.tmpdir") + "a/b/c") + "");
        FileUtil.deleteDirectory(System.getProperty("java.io.tmpdir") + "a/b/c");
    }

    @Test
    public void testMatch(){
        String path1 = "target/abc.war";
        String path2 = "chaos-web/target/abc.war";
        String path3 = "chaos-static/target/abc.war";
        String path4 = "chaos-static/web/resources/env-config.properties";
        String jenkinsReg = "*/web/resources/env-config.properties";
        String reg = "";
        reg = jenkinsReg.replace("*","([\\S]*)");
        logger.info("reg:" + reg);
        logger.info("匹配结果: " + FileUtil.isMatch(path1, reg));
        logger.info("匹配结果: " + FileUtil.isMatch(path2, reg));
        logger.info("匹配结果: " + FileUtil.isMatch(path3, reg));
        logger.info("匹配结果: " + FileUtil.isMatch(path4, reg));
    }

}
