package gr.betclient.async;

import gr.betclient.model.user.User;
import gr.betclient.model.user.UserBet;

import java.io.Serializable;
import java.util.List;

public interface AsyncHolder {
	
	void onAsyncEventsFinished(List<? extends Serializable> objectList);
	
	void onAsyncLeaderboardFinished(List<? extends Serializable> objectList);
	
	void onAsyncPlaceBetFinished(UserBet userBet);
	
	void onAsyncCreateUserSuccess(User user);

}
