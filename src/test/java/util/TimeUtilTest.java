package util;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xuyexin on 16/2/22.
 */
public class TimeUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(TimeUtilTest.class);


	@Test
	public void testTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));
		System.out.println("1:" + dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));
		System.out.println("2:" + dateFormat.format(DateUtils.truncate(date, Calendar.HOUR_OF_DAY)));
		System.out.println("3:" + dateFormat.format(DateUtils.truncate(date, Calendar.MINUTE)));//2009-08-04 16:23:00
		System.out.println("4:" + dateFormat.format(DateUtils.truncate(date, Calendar.SECOND))); //2009-08-04 16:23:14
		System.out.println("5:" + dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));
		System.out.println("6:" + dateFormat.format(DateUtils.truncate(date, Calendar.MONTH))); //2009-08-01 00:00:00
		System.out.println("7:" + dateFormat.format(DateUtils.truncate(date, Calendar.YEAR))); //2009-01-01 00:00:00
		logger.info("========");
	}

	@Test
	public void testGetTime() {
		ArrayList<Long> years = TimeDevideUtil.listYearTimes(1);
		for (Long year : years){
			printTime(new Date(year));
		}
		System.out.println("========");

		ArrayList<String> yearNames = TimeDevideUtil.listYearNames(1);
		for (String year:yearNames){
			System.out.println(year);
		}
	}


	public static void printTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		System.out.println(dateStr);
	}

}
