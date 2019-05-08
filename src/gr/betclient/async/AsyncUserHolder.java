package gr.betclient.async;

import android.os.AsyncTask;
import gr.betclient.model.user.User;

/**
 * Interface for GET/POST actions that demand a {@link User} to be returned after the 
 * process performed by an {@link AsyncTask}
 *
 */
public interface AsyncUserHolder {
	
	void onAsyncGetUserFinished(User user);

}
