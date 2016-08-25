package util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by xuyexin on 16/5/11.
 */
public class ShellExecutor {

	public static String exec(String command, Logger log) throws Exception{
		try{
			Process pr = Runtime.getRuntime().exec(command);

			BufferedReader in = new BufferedReader(new
				InputStreamReader(pr.getInputStream()));
			String line;
			String result ="";
			while ((line = in.readLine()) != null) {
				result += line +"\n";
				if(log != null){
					log.info(line);
				}
			}
			in.close();
			pr.waitFor();
			return result;
		} catch (Exception e){
			if (log != null) {
				log.info(StackTransfer.getString(e));
			}
			throw e;
		}
	}

}
