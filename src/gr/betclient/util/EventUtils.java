package gr.betclient.util;

import gr.betclient.data.AppConstants;
import gr.betclient.model.event.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventUtils {
	
	/**
     * TODO: must apply time zone differences.
     * 
     * @param event
     * @return
     */
    public static boolean isInTheFuture(Event event) {
    	SimpleDateFormat df = new SimpleDateFormat(AppConstants.EVENT_DATE_FORMAT);
    	Date eventDate;
		try {
			eventDate = df.parse(event.getMatchDate() + AppConstants.SPACE + event.getMatchTime());
			if (eventDate.after(new Date())){
	    		return true;
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
