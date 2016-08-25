package util.jsch;
/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/3/12
 */
public class SshDriver {
    private final Logger log = LoggerFactory.getLogger(SshDriver.class);
    /**
     * 线程读日志时间间隔
     */
    public static final int RUNNER_INTERVAL = 500;
    /**
     * 执行时间超时
     */
    public static final int RUNNER_TIMEOUT = 600000;
    /**
     * 用户名
     */
    String userName;
    /**
     * 服务器密码
     */
    String passWord;
    /**
     * 服务器地址
     */
    String host;
    /**
     * 要执行的命令
     */
    String command;
    /**
     * 返回日志
     */
    StringBuffer resultBuffer;
    /**
     * 命令执行完的状态码(-1,还未执行，0 执行完成，>1服务器返回的错误码，-2 正在执行)
     */
    StringBuffer exitCode;
    /**
     * 调用组件
     */
    JSch jSch;
    /**
     * 会话
     */
    Session session;
    /**
     * 连接通道
     */
    Channel channel;
    /**
     * 日志监控器
     */
    SshRunner runner;
    /**
     * 执行器状态(-1 未执行，-3 执行发生异常，0执行完毕)
     */
    StringBuffer status;
    /**
     * 错误信息
     */
    StringBuffer error;

    public SshDriver(String userName, String passWord, String host) {
        this.userName = userName;
        this.passWord = passWord;
        this.host = host;
        this.resultBuffer = new StringBuffer();
        this.exitCode = new StringBuffer().append("-1");
        this.status = new StringBuffer().append("-1");
        this.runner = new SshRunner();
        this.error = new StringBuffer();
    }

    /**
     * 执行函数
     */
    public void execCommand() {
        try {
            jSch = new JSch();
            session = jSch.getSession(userName, host, 22);
            UserInfo ui = new SshUserInfo(passWord);
            session.setUserInfo(ui);
            session.connect();
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setOutputStream(System.out);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
            this.status = status.delete(0, status.length()).append("-2");
            startLogWatch();
        } catch (Exception e) {
            log.info("远程执行ssh异常:", e);
            this.status = status.delete(0, status.length()).append("-3");
            error.append(e.toString() + "\n");
        }
    }

    /**
     * 起线程监控log
     */
    private void startLogWatch(){
        runner.setChannel(channel);
        runner.setSession(session);
        runner.setResultBuffer(resultBuffer);
        runner.setExitCode(exitCode);
        runner.setStatus(status);
        runner.setError(error);
        new Thread(this.runner).start();
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
                if (channel.getExitStatus() > 0){
                   InputStream errorInputStream =  ((ChannelExec) channel).getErrStream();
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

        public void setResultBuffer(StringBuffer resultBuffer) {
            this.resultBuffer = resultBuffer;
        }

        public void setExitCode(StringBuffer exitCode) {
            this.exitCode = exitCode;
        }

        public void setStatus(StringBuffer status) {
            this.status = status;
        }

        public void setError(StringBuffer error) {
            this.error = error;
        }
    }


    /**
     * 内部用户类
     */
    public static class SshUserInfo implements UserInfo {
        public SshUserInfo(String passwd) {
            this.passwd = passwd;
        }

        public String getPassword() {
            return passwd;
        }

        public boolean promptYesNo(String str) {
            return true;
        }

        String passwd;

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return true;
        }

        public boolean promptPassword(String message) {
            return true;
        }

        public void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }
    }

    public String getUserName() {
        return userName;
    }


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHost() {
        return host;
    }

    public String getCommand() {
        return command;
    }

    public StringBuffer getStatus(){
        return status;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public StringBuffer getResultBuffer() {
        return resultBuffer;
    }


    public StringBuffer getExitCode() {
        return exitCode;
    }


    public StringBuffer getError() {
        return error;
    }
}