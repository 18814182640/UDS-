package user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String DATA_FORMAT="yyyy-MM-dd";
	public static final String DATA_FULL_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String DATA_FULL_FORMAT2="yyyy-MM-dd HH_mm_ss";
	
	public static Date getFormatDate(String date){
		//System.out.println("date = " + date);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
			if(date!=null){
				return sdf.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String formatDate(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
			if(date!=null){
				return sdf.format(date);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	
	public static String formatFullDate(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_FULL_FORMAT);
			if(date!=null){
				return sdf.format(date);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	public static String formatFullDate2(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_FULL_FORMAT2);
			if(date!=null){
				return sdf.format(date);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	public static Date getFormatFullDate(String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_FULL_FORMAT);
			if(date!=null){
				return sdf.parse(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static int getYear(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			
			return cal.get(Calendar.YEAR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} 
	
	public static int getMonth(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH)+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} 
	
	public static int getDate(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	} 
	
	
	public static int getHour(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR_OF_DAY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} 
	
	public static int getMinute(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MINUTE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} 
	public static int getSecond(Date date){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.SECOND);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} 
	public static long sum(Date d1,Date d2){
		long reval = d1.getTime() + d2.getTime();
		return reval;
	}
	public static long sub(Date d1,Date d2){
		long reval = d1.getTime() - d2.getTime();
		return Math.abs(reval);
	}
	
	public static int getMonth(long millis){
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(Math.abs(millis));
		int reval = (cl.get(Calendar.YEAR)-1970)*12 + cl.get(Calendar.MONTH);
		return reval;
	}
	public static long getDay(long millis){
		return Math.abs(millis)/(1000*3600*24l);
	}
	public static int getYear(long millis){
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(Math.abs(millis));
		int reval = (cl.get(Calendar.YEAR)-1970);
		return reval;
	}
}
