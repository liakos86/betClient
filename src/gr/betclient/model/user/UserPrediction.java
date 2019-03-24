package gr.betclient.model.user;

import java.io.Serializable;

import gr.betclient.model.enums.BetStatus;


public class UserPrediction implements Serializable{
	
	private static final long serialVersionUID = 1L;

	String predictionId;
	
	String eventId;
	
	String userId;
	
	String prediction;
	
	BetStatus status;
	
	String result;
	
	Integer betAmount;
	
	public void setPredictionId(String predictionId) {
		this.predictionId = predictionId;
	}
	
	public String getPredictionId() {
		return predictionId;
	}

	public Integer getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Integer betAmount) {
		this.betAmount = betAmount;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public BetStatus getStatus() {
		return status;
	}

	public void setStatus(BetStatus status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	

}
