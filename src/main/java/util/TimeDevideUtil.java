package util;

import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xuyexin on 16/2/22.
 */
public class TimeDevideUtil {

	/**
	 * 获取最近的年份
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<String> listYearNames(int n) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		ArrayList<String> yearsList = new ArrayList<String>();
		for (int i = year - n; i <= year; i++)
			yearsList.add(i + "");
		return yearsList;
	}


	/**
	 * 获取最近的年份时间列表
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<Long> listYearTimes(int n) {
		ArrayList<Long> resultList = new ArrayList<Long>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for (int i = year - n; i <= year; i++){
			calendar.set(Calendar.YEAR,i);
			resultList.add(getYearLastMoment(calendar.getTime()).getTime());
		}
		return resultList;
	}

	/**
	 * 获取最近的月份
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<String> listMonthNames(int n) {
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> monthList = new ArrayList<String>();
		for (int i = -n; i <= 0; i++){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(calendar.getTime());
			calendar1.add(Calendar.MONTH,i);
			monthList.add(calendar1.get(Calendar.YEAR) +"." + (calendar1.get(Calendar.MONTH)+1));
		}
		return monthList;
	}


	/**
	 * 获取最近的月份时间列表
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<Long> listMonthTimes(int n) {
		ArrayList<Long> resultList = new ArrayList<Long>();
		Calendar calendar = Calendar.getInstance();
		for (int i = -n; i <=0; i++){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(calendar.getTime());
			calendar1.add(Calendar.MONTH, i);
			resultList.add(getMonthLastMoment(calendar1.getTime()).getTime());
		}
		return resultList;
	}

	/**
	 * 获取最近的日期
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<String> listDayNames(int n) {
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> monthList = new ArrayList<String>();
		for (int i = -n; i <= 0; i++){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(calendar.getTime());
			calendar1.add(Calendar.DAY_OF_MONTH,i);
			monthList.add(calendar1.get(Calendar.MONTH)+1 +"." + calendar1.get(Calendar.DAY_OF_MONTH));
		}
		return monthList;
	}

	/**
	 * 获取最近的日期时间列表
	 *
	 * @param n
	 * @return
	 */
	public static ArrayList<Long> listDayTimes(int n) {
		ArrayList<Long> resultList = new ArrayList<Long>();
		Calendar calendar = Calendar.getInstance();
		for (int i = -n; i <=0; i++){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(calendar.getTime());
			calendar1.add(Calendar.DAY_OF_YEAR, i);
			resultList.add(getDayLastMoment(calendar1.getTime()).getTime());
		}
		return resultList;
	}


	/**
	 * 获取某年最后一个时刻
	 */
	public static Date getYearLastMoment(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR,1);
		date =calendar.getTime();
		Date newYear = DateUtils.truncate(date, Calendar.YEAR);
		Date yearLastMoment = new Date(newYear.getTime()-1);
		return yearLastMoment;
	}

	/**
	 * 获取某月最后一个时刻
	 */
	public static Date getMonthLastMoment(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,1);
		date =calendar.getTime();
		Date newMonth = DateUtils.truncate(date, Calendar.MONTH);
		Date monthLastMoment = new Date(newMonth.getTime()-1);
		return monthLastMoment;
	}

	/**
	 * 获取某天最后一个时刻
	 */
	public static Date getDayLastMoment(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,1);
		date =calendar.getTime();
		Date newDay = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		Date dayLastMoment = new Date(newDay.getTime()-1);
		return dayLastMoment;
	}

}

