package util.jsch;
/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.InputStream;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/3/12
 */
public class SshExcutor {
    private final Logger log = LoggerFactory.getLogger(SshExcutor.class);

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
    /**
     * 错误信息
     */
    StringBuffer error;

    public SshExcutor(String userName, String passWord, String host) {
        this.userName = userName;
        this.passWord = passWord;
        this.host = host;
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
        } catch (Exception e) {
            log.info("远程执行ssh异常:", e);
            error.append(e.toString() + "\n");
        }
    }


    /**
     * 执行函数
     */
    public void uploadFile() {
        try {
            jSch = new JSch();
            session = jSch.getSession(userName, host, 22);
            UserInfo ui = new SshUserInfo(passWord);
            session.setUserInfo(ui);
            session.connect();
            channel = session.openChannel("sftp");
            ChannelSftp sftp =((ChannelSftp) channel);
            sftp.connect(1000);
            sftp.cd("/opt");
            sftp.put("/Users/xuyexin/Downloads/abc.txt","abdc.txt");
        } catch (Exception e) {
            log.info("远程执行sftp异常:", e);
            error.append(e.toString() + "\n");
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

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setHost(String host) {
        this.host = host;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public JSch getjSch() {
        return jSch;
    }

    public void setjSch(JSch jSch) {
        this.jSch = jSch;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public StringBuffer getError() {
        return error;
    }

    public void setError(StringBuffer error) {
        this.error = error;
    }
}