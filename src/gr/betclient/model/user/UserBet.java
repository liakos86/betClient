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

	String mongoId;
	
	String mongoUserId;
	
	String betStatus;
	
	Integer betAmount;
	
	String betPlaceDate;
	
	List<UserPrediction> predictions = new ArrayList<UserPrediction>();
	
	
	public String getBetPlaceDate() {
		return betPlaceDate;
	}

	public void setBetPlaceDate(String betPlaceDate) {
		this.betPlaceDate = betPlaceDate;
	}

//	public String getBetId() {
//		return betId;
//	}
//
//	public void setBetId(String betId) {
//		this.betId = betId;
//	}
	
	

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


	public String getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(String status) {
		this.betStatus = status;
	}
	
	public String getMongoId() {
		return mongoId;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	public String getMongoUserId() {
		return mongoUserId;
	}

	public void setMongoUserId(String mongoUserId) {
		this.mongoUserId = mongoUserId;
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
		userBet.setMongoId(null);
		userBet.setBetStatus("open");
		userBet.getPredictions().clear();
		return userBet;
	}

	public Double getPossibleEarnings() {
		 Double possibleEarnings = this.getBetAmount().doubleValue();
	      for (UserPrediction userPrediction : this.getPredictions()) {
			possibleEarnings *= userPrediction.getOddValue();
		}
	    return possibleEarnings;
	}


}
