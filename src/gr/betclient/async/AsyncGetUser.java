package gr.betclient.async;

import gr.betclient.data.AppConstants;
import gr.betclient.data.UrlConstants;
import gr.betclient.model.user.User;
import gr.betclient.util.HttpHelper;

import java.io.IOException;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncGetUser extends AsyncTask<Void, Void, User> {
	
	User user;
	
	AsyncUserHolder asyncUserHolder;
	
	
	public AsyncGetUser(AsyncUserHolder asyncUserHolder, User user) {
		this.asyncUserHolder = asyncUserHolder;
		this.user = user;
	}
	
    @Override
    protected User doInBackground(Void... unused) {
        String userString = null;
        try {
            userString = new HttpHelper().fetchGetContent(UrlConstants.GET_USER.replace(AppConstants.USER_ID, user.getMongoId()));
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
        user = new Gson().fromJson(userString, new TypeToken<User>() {
        }.getType());

        return user;
    }

	@Override
    protected void onPostExecute(User user) {
    	asyncUserHolder.onAsyncGetUserFinished(user);
    }
   
}

