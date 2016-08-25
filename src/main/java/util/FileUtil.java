package util;

import net.sf.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/10/15
 */
public class FileUtil {

	/**
	 * 查找单个文件
	 *
	 * @param dirPath
	 * @param reg
	 * @param ignoreDir
	 * @return
	 */
	public static File findOneFile(String dirPath, String reg, String ignoreDir) {
		File file = new File(dirPath);
		File result = null;

		//忽略特定名字的文件夹
		if (ignoreDir != null && !"".equals(ignoreDir) && ignoreDir.equals(file.getName()))
			return null;

		for (File f : file.listFiles()) {
			if (result != null) {
				return result;
			}
			if (f.isDirectory()) {
				result = findOneFile(f.getPath(), reg, ignoreDir);
			} else {
				if (isMatch(f.getPath(), reg)) {
					return new File(f.getPath());
				}
			}
		}
		return result;
	}

	/**
	 * 查找多个文件
	 *
	 * @param dirPath
	 * @param reg
	 * @param ignoreDir
	 * @param resultFiles
	 */
	public static void findFiles(String dirPath, String reg, String ignoreDir, List<File> resultFiles) {
		File file = new File(dirPath);

		//忽略特定名字的文件夹
		if (ignoreDir != null && !"".equals(ignoreDir) && ignoreDir.equals(file.getName()))
			return;

		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				findFiles(f.getPath(), reg, ignoreDir, resultFiles);
			} else {
				if (isMatch(f.getPath(), reg)) {
					resultFiles.add(f);
				}
			}
		}
	}

	/**
	 * 写文件
	 *
	 * @param content
	 * @param configFile
	 * @return
	 */
	public static boolean writeFile(String content, String configFile) {
		File file = new File(configFile);
		FileWriter fileWriter = null;

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 *
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) break;
			}
		}
		if (!flag) return false;
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除其他文件
	 *
	 * @param path
	 * @param filePaths
	 * @param resultFiles
	 */
	public static void deleteOtherFile(String path, JSONArray filePaths, List<File> resultFiles) {
		File file = new File(path);
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				deleteOtherFile(f.getPath(), filePaths, resultFiles);
				if (null == f.listFiles() || 0 == f.listFiles().length) {
					f.delete();
				}
			} else {
				boolean flag = false;
				for (int i = 0; i < filePaths.size(); i++) {
					String filePath = filePaths.getString(i);
					if (f.getPath().endsWith(filePath)) {
						flag = true;
					}
				}
				if (!flag) {
					f.delete();
				} else {
					resultFiles.add(f);
				}
			}
		}
	}

	/**
	 * 匹配路径是否相同
	 *
	 * @param path
	 * @param regular
	 * @return
	 */
	public static boolean isMatch(String path, String regular) {
		if (null == path || null == regular)
			return false;
		try {
			if (path.matches(regular)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建目录
	 *
	 * @return
	 */
	public static boolean createDir(String path) {

		File dir = new File(path);
		if (dir.exists()) {
			if (dir.isDirectory())
				return true;
			else
				return false;
		}

		dir.mkdirs();
		if (dir.exists() && dir.isDirectory())
			return true;
		else
			return false;
	}

}