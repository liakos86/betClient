package gr.betclient.data;

public interface UrlConstants {
	
	//TODO: when server deployment ready, replace with public urls
	
	public static final String GET_COUNTRIES_WITH_COMPETITIONS_URL = "http://10.0.3.2:8080/betServer/ws/betServer/getEvents";

	public static final String POST_PLACE_BET = "http://10.0.3.2:8080/betServer/ws/betServer/placeBet";

	public static final String GET_LEADERBOARD = "http://10.0.3.2:8080/betServer/ws/betServer/getLeaderBoard";

	public static final String GET_USER = "http://10.0.3.2:8080/betServer/ws/betServer/"+ AppConstants.USER_ID +"/get";
	
	public static final String POST_CREATE_USER = "http://10.0.3.2:8080/betServer/ws/betServer/createUser";
	

}
