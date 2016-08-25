package util;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyexin on 16/5/12.
 */
public class RsyncUtil {
	private static final Logger log = LoggerFactory.getLogger(RsyncUtil.class);

	/**
	 * 获取增量文件包
	 * @param originDir
	 * @param remoteDir
	 * @param targetFile
	 * @param host
	 * @param user
	 * @param pass
	 * @param log
	 * @return
	 * @throws Exception
	 */
	public static String getIncrementPackage(String originDir, String remoteDir, String targetFile, String host, String user, String pass,
	                                         Logger log) throws Exception {
		String command = "sshpass -p " + pass
			+ " rsync -zvacn " + originDir
			+ " -e ssh " + user + "@" + host + ":" + remoteDir;

		String execResult = ShellExecutor.exec(command, log);

		if (null == execResult)
			throw new NullPointerException();
		if (execResult.equals(""))
			return null;
		log.info("开始过滤增量文件");
		JSONArray fileArray = getIncrementFiles(execResult,originDir);
		log.info("过滤后的增量文件个数：" + fileArray.size());
		List<File> fileResultList = new ArrayList<File>();
		log.info("开始删除其他文件");
		FileUtil.deleteOtherFile(originDir, fileArray, fileResultList);
		log.info("开始压缩文件");
		CompressUtil.dirToTargz(originDir,targetFile,originDir);
		return targetFile;

	}

	/**
	 * 过滤掉不需要的文件
	 * @param originStr
	 * @param originDir
	 * @return
	 */
	private static JSONArray getIncrementFiles(String originStr, String originDir) {

		JSONArray fileList = new JSONArray();
		String fileStringList[] = originStr.split("\n");
		for (String str : fileStringList) {
			if ("./".equals(str))
				continue;
			File file = new File(originDir + str);

			if (!file.exists())
				continue;
			fileList.add(file.getPath());
		}
		return fileList;
	}


}
