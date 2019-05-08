package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.event.CountryWithCompetitions;
import gr.betclient.util.HttpHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncGetCountriesWithCompetitions 
extends AsyncTask<Void, Void, List<CountryWithCompetitions>> {
	
	List<CountryWithCompetitions> countriesWithComp = new ArrayList<CountryWithCompetitions>();
	
	AsyncHolder holder;
	
	public AsyncGetCountriesWithCompetitions(AsyncHolder holder) {
		this.holder = holder;
	}

    @Override
    protected List<CountryWithCompetitions> doInBackground(Void... unused) {
        String content = null;

        try {
            content = new HttpHelper().fetchGetContent(UrlConstants.GET_COUNTRIES_WITH_COMPETITIONS_URL);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        countriesWithComp  = new Gson().fromJson(content, new TypeToken<List<CountryWithCompetitions>>() {
        }.getType());

        return null;
    }

	@Override
    protected void onPostExecute(List<CountryWithCompetitions> result) {
        holder.onAsyncEventsFinished(countriesWithComp);
    }
   
}