package util;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by xuyexin on 15/12/31.
 */
public class PythonDriver {

	//获取当前路径
	public static String getPath(String fileName){
		try {
			return ClassLoader.getSystemResource("python" + File.separator + fileName ).getPath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	};

	//执行python文件
	public static void execPython(String filePath, String param ) throws Exception {
		try{
			Process pr = Runtime.getRuntime().exec("python " + filePath + " " + param.trim());

			BufferedReader in = new BufferedReader(new
				InputStreamReader(pr.getInputStream()));
			String line;
			System.out.println("Start");
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			pr.waitFor();
		} catch (Exception e){
			throw e;
		}
	}
}


