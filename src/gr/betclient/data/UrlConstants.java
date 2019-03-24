package gr.betclient.data;

public interface UrlConstants {
	
	//TODO: when server deployment ready, replace with public urls
	
	public static final String EVENTS = "http://10.0.3.2:8080/betServer/ws/betServer/getEvents";

	public static final String PLACE_BET = "http://10.0.3.2:8080/betServer/ws/betServer/placeBet";

	public static final String LEADERBOARD = "http://10.0.3.2:8080/betServer/ws/betServer/getLeaderBoard";

}
