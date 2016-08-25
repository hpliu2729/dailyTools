package util.jsch;

import org.junit.Test;

/**
 * Created by xuyexin on 16/3/14.
 */
public class SshExecutorTest {

	@Test
	public void testExecuteAndMonitor() {
		SshExcutor sshExcutor = new SshExcutor("root", "Manager1", "112.74.217.86");
		for (int i = 0; i < 2; i++) {

			System.out.println("------开始------");

			sshExcutor.setCommand("/data/abc.sh chaos " + i + " 许业欣");
			sshExcutor.execCommand();
			new SshMonitor(sshExcutor).startLogWatch();
			System.out.println("------结束------");

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	@Test
	public void testUpload() {
		SshExcutor sshExcutor = new SshExcutor("root", "000", "112.74.217.86");
		sshExcutor.uploadFile();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
}


