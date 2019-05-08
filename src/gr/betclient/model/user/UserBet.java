package gr.betclient.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A bet that is placed by a {@link User}.
 * Involves a list of predictions.
 * 
 * @author liakos
 *
 */
public class UserBet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String betId;
	
	String userId;
	
	String betStatus;
	
	Integer betAmount;
	
	List<UserPrediction> predictions = new ArrayList<UserPrediction>();
	
	public String getBetId() {
		return betId;
	}

	public void setBetId(String betId) {
		this.betId = betId;
	}

	public List<UserPrediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<UserPrediction> predictions) {
		this.predictions = predictions;
	}

	public Integer getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Integer betAmount) {
		this.betAmount = betAmount;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(String status) {
		this.betStatus = status;
	}

	public static void copyFields(UserBet source, UserBet destination) {
		destination.setBetAmount(source.getBetAmount());
		destination.setBetStatus(source.getBetStatus());

		for (UserPrediction sourcePrediction : source.getPredictions()) {
			for (UserPrediction destPrediction : destination.getPredictions()) {
				if (sourcePrediction.getEventId().equals(destPrediction.getEventId())){
					UserPrediction.copyFields(sourcePrediction, destPrediction);
				}
			}
		}
		
	}

	public static UserBet clear(UserBet userBet) {
		userBet.setBetAmount(0);
		userBet.setBetId(null);
		userBet.setBetStatus("open");
		userBet.getPredictions().clear();
		return userBet;
	}


}
