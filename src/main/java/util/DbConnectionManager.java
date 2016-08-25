package util;

import util.ConfigDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接管理器
 */
public class DbConnectionManager {
	
	/**
	 * 驱动
	 */
	private static String DBDRIVER;
	
	/**
	 * 链接地址
	 */
	private static String URL;
	


	/**
	 * 私有化构造器
	 */
	private DbConnectionManager(){}
	
	/**
	 * 获取数据库连接
	 */
	public static final Connection getConnection(){
		Connection connection = null;
		DBDRIVER = ConfigDriver.getString("db.driver");
		URL = ConfigDriver.getString("db.url");
		try {
			Class.forName(DBDRIVER);
			connection = DriverManager.getConnection(URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
