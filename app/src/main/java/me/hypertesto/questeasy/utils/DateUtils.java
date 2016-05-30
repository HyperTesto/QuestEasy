package me.hypertesto.questeasy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rigel on 23/05/16.
 */
public class DateUtils {

	public static final	SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat SDF_ALT = new SimpleDateFormat("dd-MM-yyyy");

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

	public static String format(Date date){
		return SDF.format(date);
	}

	public static String formatForFileName(Date date) { return SDF_ALT.format(date); }

	public static Date parse(String input){
		try {
			return SDF.parse(input);
		} catch (ParseException e){
			throw new RuntimeException(e);
		}
	}
}
