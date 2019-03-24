package gr.betclient.act;

import gr.betclient.data.AppConstants;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class BetClientApplication
extends Application{
	
	
	
	
	  @Override
	    public void onCreate() {
	        super.onCreate();

	        SharedPreferences app_preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
	        
	        
	        if (app_preferences.getString(AppConstants.USER_IN_PREFS, null) == null) {
//	            SharedPreferences.Editor editor = app_preferences.edit();
//	            editor.putString(USER_IN_PREFS, "STARTED");
//	            editor.putString(USER_IN_PREFS, "STOPPED");
//	            editor.apply();
	        }

	    }

}
