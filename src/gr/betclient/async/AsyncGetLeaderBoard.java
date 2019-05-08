package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.user.User;
import gr.betclient.util.HttpHelper;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncGetLeaderBoard extends AsyncTask<Void, Void, List<User>> {
	
	List<User> users;
	
	AsyncHolder holder;
	
	public AsyncGetLeaderBoard(AsyncHolder holder) {
		this.holder = holder;
	}

    @Override
    protected List<User> doInBackground(Void... unused) {
        String content = null;

        try {
            content = new HttpHelper().fetchGetContent(UrlConstants.GET_LEADERBOARD);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
        users = new Gson().fromJson(content, new TypeToken<List<User>>() {
        }.getType());

        return users;
    }

	@Override
    protected void onPostExecute(List<User> result) {
    	holder.onAsyncLeaderboardFinished(result);
    }
   
}

