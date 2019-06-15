package gr.betclient.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	
	public static String getDate(int offset){
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE, offset);
		Date date = instance.getTime();
		return new SimpleDateFormat("YYYY-MM-dd").format(date);
	}

}
