package util.jsch;

import org.junit.Test;

/**
 * Created by xuyexin on 16/5/27.
 */
public class SshFtpUtilTest {


	@Test
	public void uploadTest(){
		try {
			SShFtpUtil.sftpFileUpload("112.74.217.86", "root", "000", 22);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
