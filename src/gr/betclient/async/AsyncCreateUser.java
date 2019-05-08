package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.user.User;
import gr.betclient.util.HttpHelper;

import java.io.IOException;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AsyncCreateUser extends AsyncTask<Void, Void, User> {
	
	User user;
	
    AsyncUserHolder asyncUserHolder;

    public AsyncCreateUser(User user, AsyncUserHolder asyncUserHolder) {
        this.user = user;
        this.asyncUserHolder = asyncUserHolder;
    }

    @Override
    protected User doInBackground(Void... unused) {
    	String content = null;
    	
        try {
            content = new HttpHelper().fetchPostContent(UrlConstants.POST_CREATE_USER, user);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
        user = new Gson().fromJson(content, new TypeToken<User>() {}.getType());

        return user;
    }

	@Override
    protected void onPostExecute(User user) {
    	asyncUserHolder.onAsyncGetUserFinished(user);
    }
}