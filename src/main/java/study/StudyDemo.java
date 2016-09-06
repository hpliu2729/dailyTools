package study;

import org.junit.Test;
import util.DbConnectionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/9/6
 */
public class StudyDemo {

	/**
	 * Hello World
	 */
	public static void main(String arg[]) {
		System.out.println("Hello World !");
	}


	/**
	 * 文件读写
	 */
	@Test
	public void fileRW() throws Exception {
		File readFile = new File("/Users/xuyexin/Downloads/readfile.txt");
		File writeFile = new File("/Users/xuyexin/Downloads/writeFile.txt");
		FileReader fileReader = new FileReader(readFile);
		FileWriter fileWriter = new FileWriter(writeFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String readStr;

		if (!writeFile.exists()) {
			writeFile.createNewFile();
		}
		while (null != (readStr = bufferedReader.readLine())) {
			System.out.println(readStr);
			fileWriter.write(readStr + "\n");
		}

		bufferedReader.close();
		fileReader.close();
		fileWriter.close();
	}

	/**
	 * 数据库操作
	 */
	@Test
	public void dbRead() throws SQLException {
		String sql = "select count(1) as deploy_count from chaos_deploy_record;";

		Connection connection = DbConnectionManager.getConnection();

		Statement statement = connection.createStatement();

		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			String deployCount = resultSet.getString("deploy_count");
			System.out.println("Chaos 总共部署了：" + deployCount + " 次项目");
		}

	}

}
