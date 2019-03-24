package gr.betclient.act;

import gr.betclient.R;
import gr.betclient.data.UrlConstants;
import gr.betclient.model.event.AndroidUpcomingEvent;
import gr.betclient.model.event.AndroidUpcomingEventsAdapterItem;
import gr.betclient.model.user.UserPrediction;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BetClientActivity 
extends Activity {

		/**
		 * 
		 */
	 	ListView androidUpcomingEventsListView;

	    AndroidUpcomingEventsAdapterItem androidUpcomingEventsListViewAdapter;

	    List<AndroidUpcomingEvent> androidUpcomingEvents = new ArrayList<AndroidUpcomingEvent>();


	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_bet_client);

	        androidUpcomingEventsListView =  (ListView) findViewById(R.id.upComingEvents);
	        androidUpcomingEventsListViewAdapter = new AndroidUpcomingEventsAdapterItem(this, this.getApplicationContext(),
	                R.layout.list_upcoming_event_row, androidUpcomingEvents);
	        androidUpcomingEventsListView.setAdapter(androidUpcomingEventsListViewAdapter);
	        new LoadAsyncEvents().execute();

	        
	        Button placeBet = (Button) findViewById(R.id.place_bet);
	        placeBet.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					placeBet(v);
				}
			});


	    }
	    



		void placeBet(View v){
	    	UserPrediction userPrediction = new UserPrediction();
			userPrediction.setUserId("android_user");
			userPrediction.setBetAmount(30);
			userPrediction.setEventId("1232313");
			userPrediction.setPrediction("X");
			
			 new PlaceAsyncBet(userPrediction).execute();
	    }


	    private class LoadAsyncEvents extends AsyncTask<Void, Void, Void> {

	        protected void onPreExecute() {
	        }


	        @Override
	        protected Void doInBackground(Void... unused) {
	            String content = null;

	            try {
	                content = fetchContent(UrlConstants.EVENTS, "GET", null);
	            }catch(IOException ioe){
	                ioe.printStackTrace();
	            }
	            Map<String, Map<String, List<AndroidUpcomingEvent>>> events = new Gson().fromJson(content, new TypeToken<Map<String, Map<String, List<AndroidUpcomingEvent>>>>() {
	            }.getType());

	            List<AndroidUpcomingEvent> epl = events.get("soccer").get("epl");
	            for (AndroidUpcomingEvent pl : epl){
	                androidUpcomingEvents.add(pl);
	            }

	           // androidUpcomingEventsListViewAdapter.notifyDataSetChanged();
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            androidUpcomingEventsListViewAdapter.notifyDataSetChanged();
	        }
	       
	    }

	    private class PlaceAsyncBet extends AsyncTask<Void, Void, Void> {
	        UserPrediction prediction;

	        public PlaceAsyncBet(UserPrediction prediction) {
	            this.prediction = prediction;
	        }

	        protected void onPreExecute() {

	        }

	        @Override
	        protected Void doInBackground(Void... unused) {
	        	String content = null;
	        	
	            try {
	                content = fetchContent(UrlConstants.PLACE_BET, "POST", prediction);
	            }catch(IOException ioe){
	                ioe.printStackTrace();
	            }
	            
	            prediction = new Gson().fromJson(content, new TypeToken<UserPrediction>() {
	            }.getType());


	            return null;
	        }

	        @SuppressLint("ShowToast")
			@Override
	        protected void onPostExecute(Void result) {

	        		Toast.makeText(getApplication(), prediction.getPredictionId(), 300).show();
	        }
	    }
	    
	    private  String fetchContent(String uri, String method, UserPrediction postObject) throws IOException {
            URL url = new URL(uri);
            final int OK = 200;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            //connection.setRequestMethod(method);
            
		if ("POST".equals(method)) {
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			
			String json = new Gson().toJson(postObject);
			OutputStream out = new BufferedOutputStream(connection.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			writer.write(json);
			writer.flush();
			writer.close();
			out.close();
		}
            
            int responseCode = connection.getResponseCode();
            if (responseCode == OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response2 = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response2.append(inputLine);
                }
                in.close();
                return response2.toString();
            }
            return null;
        }
	    
	    
}
