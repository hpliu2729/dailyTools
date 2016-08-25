package util;

import org.junit.Test;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/12/3
 */
public class CompressUtilTest {


    @Test
    public void testTarZip(){
        try {
            CompressUtil.unWar("D:\\temp\\earth.war", "D:\\temp\\earth");
            CompressUtil.unTargz("D:\\temp\\static.tar.gz", "D:\\temp\\static");
            CompressUtil.toWar("/Users/xuyexin/Downloads/autobuild", "/Users/xuyexin/Downloads/autobuild.war", "/Users/xuyexin/Downloads/autobuild");
            CompressUtil.dirToTargz("/Users/xuyexin/Downloads/autobuild", "/Users/xuyexin/Downloads/temp/autobuild.tar.gz", "/Users/xuyexin/Downloads/autobuild");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
