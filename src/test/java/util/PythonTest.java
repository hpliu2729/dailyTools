package util;

import net.sf.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xuyexin on 15/12/31.
 */
public class PythonTest {

	private final static Logger logger = LoggerFactory.getLogger(PythonTest.class);

	@Test
	@Ignore
	public void testPythonDriver(){
		JSONObject param = new JSONObject();
		param.put("db_name","oneplus_chaos");
		param.put("host","172.21.107.208");
		param.put("port","3306");
		param.put("password","");
		param.put("user","root");
		param.put("type","check");
		param.put("inception_host","172.21.107.100");
		param.put("inception_port",6669);
		param.put("sql_content","insert into inception_test(user_name,age) values('xuyex44in1',88);");

		String path = PythonDriver.getPath("api.py");
		String paramStr = param.toString().trim();
		paramStr = paramStr.replaceAll(" ","&nbsp_in_chaos");

		try {
			PythonDriver.execPython(path, paramStr);
		}catch (Exception e){
			System.out.println("Error execute python");
		}
	}

	@Test
	public void testPythonDriver2(){
		String str = "{\"backup_db_host\":\"172.21.107.41\",\"backup_db_name\":\"172_21_107_123_3306_oneplus_wms\",\"backup_db_pass\":\"oneplus818\",\"backup_db_port\":3306,\"backup_db_user\":\"xuyexin\",\"backup_opid_time\":\"1454063540_17_1\",\"type\":\"roll_back\"}";
		String paramStr = str.trim();

		try {
			PythonDriver.execPython("/Users/xuyexin/Work/chaos-scripts/inception/api_test.py ", paramStr);
		}catch (Exception e){
			System.out.println("Error execute python");
		}
	}

}
