/******************************************************************************
* Copyright (C) 2014 ShenZhen Oneplus Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳万普拉斯科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
*****************************************************************************/
package util.fastDfs;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import util.ConfigDriver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * FastDFS分布式文件系统管理器
 * @Title: FastDFSManager.java
 * @Package com.oneplus.base.util.fastdfs
 * @Description: 
 * @version V1.0
 * @author JiangMaoHui
 * @date 2014年12月1日 下午8:38:47
 */
@Component("fastDFSManager")
public class FastDFSManager {
	
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(FastDFSManager.class);
	
	private String connectTimeout;
	
	private String networkTimeout;
	
	private String charset;

	private String trackerServers;
	
	private String trackerHttpPort;

	private String antiStealToken;
	
	private String secretKey;
	
	/**
	 * tracker server 服务接口
	 */
	private TrackerServer trackerServer;
	
	/**
	 * FastDFS链接初始化入口
	 * @date 2014年12月1日 下午9:10:29
	 */
	public  FastDFSManager(){
		this.connectTimeout = ConfigDriver.getString("connect_timeout");
		this.networkTimeout = ConfigDriver.getString("network_timeout");
		this.charset = ConfigDriver.getString("charset");
		this.trackerServers = ConfigDriver.getString("tracker_server");
		this.trackerHttpPort = ConfigDriver.getString("http.tracker_http_port");
		this.antiStealToken = ConfigDriver.getString("http.anti_steal_token");
		this.secretKey = ConfigDriver.getString("secret_key");
		init();
	}
	private void init(){
		try {
			// 初始化fasterDFS服务的配置信息
			try {
				this.initFastDFSConfig();
			} catch (Exception e) {
				throw new IllegalArgumentException("Init fastDFS config exception. ");
			}
			
			// 链接服务器
			trackerServer = new TrackerClient().getConnection();
			boolean isCon = ProtoCommon.activeTest(trackerServer.getSocket());
			if(isCon){
				logger.info("Connect the FastDFS tracker server success. ");
			} else {
				logger.error("Connect the FastDFS tracker server fail. ", new Exception("Connect the FastDFS tracker server fail."));
			}
		} catch (IOException e) {
			logger.error("链接FastDFS服务器异常", e);
		} catch (Exception e) {
			logger.error("配置连接异常", e);
		}
	}
	
	/**
	 * 获取StorageClient
	 * @date 2014年12月1日 下午8:40:40
	 */
	public StorageClient getStorageClient(){
		return new StorageClient(trackerServer, null);
	}
	
	/**
	 * 初始化fasterDF配置参数
	 * @date 2015年12月11日 下午4:23:26
	 */
	public void initFastDFSConfig() {
		// 链接超时
  		if(!StringUtils.isEmpty(connectTimeout)){
  			ClientGlobal.setG_connect_timeout(Integer.valueOf(connectTimeout));
  		} else {
  			ClientGlobal.setG_connect_timeout(ClientGlobal.DEFAULT_CONNECT_TIMEOUT);
  		}
  		
  		// 网络超时
  		if(!StringUtils.isEmpty(networkTimeout)){
  			ClientGlobal.setG_network_timeout(Integer.valueOf(networkTimeout));
  		} else {
  			ClientGlobal.setG_network_timeout(ClientGlobal.DEFAULT_NETWORK_TIMEOUT);
  		}

  		// 编码
  		if(!StringUtils.isEmpty(charset)){
  			ClientGlobal.setG_charset(charset);
  		} else {
  			ClientGlobal.setG_charset("ISO8859-1");
  		}
  		
  		// 服务器
  		if(!StringUtils.isEmpty(trackerServers)){
  			InetSocketAddress[] addresss = this.getTrackerServerArray(trackerServers);
  			ClientGlobal.setG_tracker_group(new TrackerGroup(addresss));
  		} else {
  			throw new NullPointerException("the value of item \"tracker_server\" is null. ");
  		}
  		
  		// 端口
  		if(!StringUtils.isEmpty(trackerHttpPort)){
  			ClientGlobal.setG_tracker_http_port(Integer.valueOf(trackerHttpPort));
  		} else {
  			ClientGlobal.setG_tracker_http_port(80);
  		}
  		
  		// token
  		if(!StringUtils.isEmpty(antiStealToken)){
			ClientGlobal.setG_anti_steal_token(this.parseAntiStealToken(antiStealToken));
		} else {
			ClientGlobal.setG_anti_steal_token(false);
		}
  		if(ClientGlobal.getG_anti_steal_token()){
  			ClientGlobal.setG_secret_key(secretKey);
  		}
	}
	
	/**
	 * 服务器地址
	 * @param trackerServers
	 * @return
	 * @date 2015年12月11日 下午3:49:57
	 */
	private InetSocketAddress[] getTrackerServerArray(String trackerServers){
		String[] trackerServerArray = trackerServers.trim().split(",");
		List<InetSocketAddress> inetSocketAddressList = new ArrayList<InetSocketAddress>();
		for(String trackerServer : trackerServerArray){
			if(StringUtils.isEmpty(trackerServer)){
				continue;
			}
			
			String[] parts = trackerServer.trim().split("\\:", 2);
  			if (parts.length != 2)
  			{
  				throw new IllegalArgumentException("the value of item \"tracker_server\" is invalid, the correct format is host:port");
  			}
  			inetSocketAddressList.add(new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim())));
		}
		if(inetSocketAddressList.size() > 0){
			return inetSocketAddressList.toArray(new InetSocketAddress[inetSocketAddressList.size()]);
		} else {
			throw new NullPointerException("the value of item \"tracker_server\" is null. ");
		}
	}
	
	/**
	 * 是否开启token
	 * @param antiStealToken
	 * @return
	 * @date 2015年12月11日 下午3:58:09
	 */
	private boolean parseAntiStealToken(String antiStealToken){
		return antiStealToken.equalsIgnoreCase("yes") || antiStealToken.equalsIgnoreCase("on") || 
				antiStealToken.equalsIgnoreCase("true") || antiStealToken.equals("1");
	}
}
