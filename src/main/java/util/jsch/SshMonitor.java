package util.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by xuyexin on 16/3/14.
 */
public class SshMonitor {

	/**
	 * 线程读日志时间间隔
	 */
	public static final int RUNNER_INTERVAL = 500;

	/**
	 * 执行时间超时
	 */
	public static final int RUNNER_TIMEOUT = 600000;

	/**
	 * 执行器
	 */
	private SshExcutor sshExcutor;

	public SshMonitor(SshExcutor sshExcutor) {
		this.sshExcutor = sshExcutor;
	}

	/**
	 * 起线程监控log
	 */
	public void startLogWatch() {
		SshRunner runner = new SshRunner();
		runner.setChannel(sshExcutor.getChannel());
		runner.setSession(sshExcutor.getSession());
		runner.setError(sshExcutor.getError());
		runner.setResultBuffer(new StringBuffer());
		runner.setExitCode(new StringBuffer().append(-1));
		runner.setStatus(new StringBuffer().append(-1));
		new Thread(runner).start();
	}

	/**
	 * 内部类，ssh执行日志监控器
	 */
	class SshRunner implements Runnable {
		Channel channel;
		Session session;
		StringBuffer resultBuffer;
		StringBuffer exitCode;
		StringBuffer status;
		StringBuffer error;
		private final Logger log = LoggerFactory.getLogger(SshRunner.class);

		@Override
		public void run() {
			try {
				InputStream inputStream = channel.getInputStream();
				byte[] tmp = new byte[1024];
				long executeTime = 0;
				while (true && executeTime < RUNNER_TIMEOUT) {
					while (inputStream.available() > 0) {
						int i = inputStream.read(tmp, 0, 1024);
						if (i < 0) break;
						String tempLog = new String(tmp, 0, i);
						log.info(tempLog);
						resultBuffer.append(tempLog);
						log.info("日志内容: " + resultBuffer.toString());
					}
					if (channel.isClosed()) {
						if (inputStream.available() > 0) continue;
						status = status.delete(0, status.length()).append("0");
						exitCode = exitCode.delete(0, exitCode.length()).append(channel.getExitStatus());
						break;
					}
					executeTime += RUNNER_INTERVAL;
					Thread.sleep(RUNNER_INTERVAL);
				}
				if (channel.getExitStatus() > 0) {
					InputStream errorInputStream = ((ChannelExec) channel).getErrStream();
					while (inputStream.available() > 0) {
						int i = errorInputStream.read(tmp, 0, 1024);
						if (i < 0) break;
						String tempLog = new String(tmp, 0, i);
						error.append(tempLog);
					}
					log.info(error.toString());
				}
				channel.disconnect();
				session.disconnect();

			} catch (Exception e) {
				log.error("远程监控ssh异常:" + session.getHost(), e);
			}
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public void setChannel(Channel channel) {
			this.channel = channel;
		}

		public void setError(StringBuffer error) {
			this.error = error;
		}

		public void setResultBuffer(StringBuffer resultBuffer) {
			this.resultBuffer = resultBuffer;
		}

		public void setExitCode(StringBuffer exitCode) {
			this.exitCode = exitCode;
		}

		public void setStatus(StringBuffer status) {
			this.status = status;
		}
	}

}
