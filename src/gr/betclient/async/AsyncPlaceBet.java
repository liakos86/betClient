package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.user.UserBet;
import gr.betclient.util.HttpHelper;

import java.io.IOException;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AsyncPlaceBet extends AsyncTask<Void, Void, UserBet> {
	
    UserBet userBet;
    
    AsyncHolder holder;

    public AsyncPlaceBet(UserBet userBet, AsyncHolder holder) {
        this.userBet = userBet;
        this.holder = holder;
    }

    @Override
    protected UserBet doInBackground(Void... unused) {
    	String content = null;
    	
        try {
            content = new HttpHelper().fetchPostContent(UrlConstants.POST_PLACE_BET, userBet);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
        userBet = new Gson().fromJson(content, new TypeToken<UserBet>() {}.getType());

        return userBet;
    }

	@Override
    protected void onPostExecute(UserBet userBet) {
    	holder.onAsyncPlaceBetFinished(userBet);
    }
}