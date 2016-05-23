package me.hypertesto.questeasy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rigel on 23/05/16.
 */
public class DateUtils {

	public static Date today(){
		return DateUtils.trimDate(new Date());
	}

	public static Date getDateInstance(int day, int month, int year){
		Calendar c = Calendar.getInstance();
		c.clear();

		//Month is 0 based
		c.set(year, month - 1, day, 0, 0, 0);

		return c.getTime();
	}

	public static Date trimDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);

		c.clear();
		c.set(year, month, day, 0, 0, 0);
		return c.getTime();
	}
}
