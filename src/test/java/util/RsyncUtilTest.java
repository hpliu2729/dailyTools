package util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsyncUtilTest {
	Logger log = LoggerFactory.getLogger(RsyncUtilTest.class);
	@Test
	public void execRsync(){
		String originDir = "/Users/xuyexin/Work/chaos/chaos-web/";
		String remoteDir = "/data/test_rsync";
		String host = "112.74.217.86";
		String user = "root";
		String pass = "000";
		String targetFile = "/Users/xuyexin/Downloads/abcd.tar.gz";

		String result = "";

		try {
			result = RsyncUtil.getIncrementPackage(originDir, remoteDir, targetFile, host, user, pass, log);
			log.info("打包后的文件地址:" + result);
		} catch (Exception e) {
			log.info("错误:" + StackTransfer.getString(e));
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		FileUtil.deleteFile(result);
		log.info("删除文件：" + result);

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
