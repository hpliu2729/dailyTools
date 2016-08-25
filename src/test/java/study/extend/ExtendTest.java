package study.extend;

import org.apache.log4j.Logger;
import org.junit.Test;


public class ExtendTest {
	public static final Logger log = Logger.getLogger(ExtendTest.class);
	
	@Test
	public void testExtnd(){
		Father fa = new Son();
		log.info("name:"+fa.getName());
		log.info("sex:"+fa.getSex());
	}
	

}
