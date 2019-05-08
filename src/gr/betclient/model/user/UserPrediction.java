package gr.betclient.model.user;

import java.io.Serializable;

public class UserPrediction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String eventId;
	
	String prediction;
	
	String result;
	
	Double multiplier;
	
	
	
	public Double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}

	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public static void copyFields(UserPrediction sourcePrediction, UserPrediction destinationPrediction) {
		destinationPrediction.setMultiplier(sourcePrediction.getMultiplier());
		destinationPrediction.setPrediction(sourcePrediction.getPrediction());
		destinationPrediction.setResult(sourcePrediction.getResult());
	}

}
