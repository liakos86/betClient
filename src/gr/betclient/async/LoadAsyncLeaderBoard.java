package gr.betclient.async;

import gr.betclient.data.UrlConstants;
import gr.betclient.model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoadAsyncLeaderBoard extends AsyncTask<Void, Void, List<User>> {
	
	List<User> users;
	
	AsyncHolder holder;
	
	public LoadAsyncLeaderBoard(AsyncHolder holder) {
		this.holder = holder;
	}

    protected void onPreExecute() {
    }


    @Override
    protected List<User> doInBackground(Void... unused) {
        String content = null;

        try {
            content = fetchContent(UrlConstants.LEADERBOARD, "GET");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
        users = new Gson().fromJson(content, new TypeToken<List<User>>() {
        }.getType());

        return users;
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void onPostExecute(List<User> result) {
    	holder.onAsyncFinished((List<? extends Serializable>) result);
    }
    
    
    private  String fetchContent(String uri, String method) throws IOException {
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

