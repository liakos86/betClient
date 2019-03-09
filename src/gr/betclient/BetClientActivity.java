package gr.betclient;

import gr.betclient.model.AndroidUpcomingEvent;
import gr.betclient.model.AndroidUpcomingEventsAdapterItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BetClientActivity extends Activity {

	 ListView androidUpcomingEventsListView;

	    AndroidUpcomingEventsAdapterItem androidUpcomingEventsListViewAdapter;

	    List<AndroidUpcomingEvent> androidUpcomingEvents = new ArrayList<AndroidUpcomingEvent>();
	    /**
	     * Called when the activity is first created.
	     */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_bet_client);

	        androidUpcomingEventsListView =  (ListView) findViewById(R.id.upComingEvents);
	        androidUpcomingEventsListViewAdapter = new AndroidUpcomingEventsAdapterItem(this, this.getApplicationContext(),
	                R.layout.list_upcoming_event_row, androidUpcomingEvents);
	        androidUpcomingEventsListView.setAdapter(androidUpcomingEventsListViewAdapter);

	        new LoadAsyncAd().execute();


	    }


	    private class LoadAsyncAd extends AsyncTask<Void, Void, Void> {

	        protected void onPreExecute() {
	        }


	        @Override
	        protected Void doInBackground(Void... unused) {
	            String content = "";
	            String url = "http://10.0.3.2:8080/betServer/ws/betServer/getEvents";
	            try {
	                content = fetchContent(url);
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


	        private  String fetchContent(String uri) throws IOException {
	            URL url = new URL(uri);
	            final int OK = 200;
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

	}
