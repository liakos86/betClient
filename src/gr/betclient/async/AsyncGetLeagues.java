package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.event.League;
import gr.betclient.util.HttpHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncGetLeagues 
extends AsyncTask<Void, Void, List<League>> {
	
	List<League> leagues = new ArrayList<League>();
	
	AsyncHolder holder;
	
	public AsyncGetLeagues(AsyncHolder holder) {
		this.holder = holder;
	}

    @Override
    protected List<League> doInBackground(Void... unused) {
        String content = null;

        try {
            content = new HttpHelper().fetchGetContent(UrlConstants.GET_LEAGUES_URL);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        leagues  = new Gson().fromJson(content, new TypeToken<List<League>>() {
        }.getType());

        return null;
    }

	@Override
    protected void onPostExecute(List<League> result) {
        holder.onAsyncEventsFinished(leagues);
    }
   
}