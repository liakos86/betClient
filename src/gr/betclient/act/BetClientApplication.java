package gr.betclient.act;

import gr.betclient.async.AsyncGetUser;
import gr.betclient.async.AsyncUserHolder;
import gr.betclient.data.AppConstants;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.User;
import gr.betclient.model.user.UserBet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BetClientApplication
extends Application
implements AsyncUserHolder {
	
	private User user;
	
	private List<UserBet> bets = new ArrayList<UserBet>();
	
	private Map<String, Event> allEventsMap = new HashMap<String, Event>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		
		
//		final SharedPreferences app_preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = app_preferences.edit();
//        editor.putString(AppConstants.PREFS_USER, null);
//        editor.apply();
		
		
		
		final SharedPreferences app_preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
		String userString = app_preferences.getString(AppConstants.PREFS_USER, null);
		if (userString == null) {
			user = null;
			return;
		}

		user = new Gson().fromJson(userString, new TypeToken<User>() {}.getType());
		for (UserBet bet : user.getUserBets()){
			bets.add(bet);
		}
		
		final AsyncUserHolder holder = this;
    	TimerTask task = new TimerTask() {
	        public void run() {
	        	new AsyncGetUser(holder, user).execute();
	        }
	    };
	    Timer timer = new Timer("Timer");
	    timer.scheduleAtFixedRate(task, 10, AppConstants.UPDATE_EVENTS_INTERVAL);
		
	}

	public User getUser() {
		return user;
	}
	
	public List<UserBet> getBets() {
		return bets;
	}

	public Map<String, Event> getAllEventsMap() {
		return allEventsMap;
	}

	@Override
	public void onAsyncGetUserFinished(User user) {
		this.user = user;
		for (UserBet userBet : user.getUserBets()) {
			boolean betExists = false;
			for (UserBet existingUserBet : new ArrayList<UserBet>(bets)){
				if (userBet.getMongoId().equals(existingUserBet.getMongoId())){
					UserBet.copyFields(userBet, existingUserBet);
					betExists = true;
					break;
				}
			}
			
			if (!betExists){
				bets.add(userBet);
			}
		}
		
		final SharedPreferences app_preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(AppConstants.PREFS_USER, new Gson().toJson(user));
        editor.apply();
	}

}
