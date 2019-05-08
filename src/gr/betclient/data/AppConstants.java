package gr.betclient.data;

import gr.betclient.model.event.Event;

public interface AppConstants {
	
	public static final int ALL_EVENTS_POSITION = 0;
	
	public static final int LIVE_EVENTS_POSITION = 1;
	
	public static final int UPCOMING_EVENTS_POSITION = 2;
	
	public static final int LEADERBOARD_POSITION = 3;
	
	public static final int MY_BETS_POSITION = 4;
	
	/**
	 * To be replaced with actual id
	 */
    public static final String USER_ID = "userId";
	
	/**
     * Shared prefs name
     */
    public static final String PREFS_NAME = "PREFS_BOUNTY_BET";
    
    public static final String PREFS_USER = "PREFS_USER";
    
    /**
     * The date format as retrieved from the server for the {@link Event} start time.
     */
    public static final String EVENT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    
    public static final String SPACE = " ";
    
    public static final Long UPDATE_EVENTS_INTERVAL = 30000L;
    
    

}
