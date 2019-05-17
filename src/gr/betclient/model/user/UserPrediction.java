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
	
	Double oddValue;
	
	String predictionDescription;
	
	public String getPredictionDescription() {
		return predictionDescription;
	}

	public void setPredictionDescription(String predictionDescription) {
		this.predictionDescription = predictionDescription;
	}

	public Double getOddValue() {
		return oddValue;
	}

	public void setOddValue(Double oddValue) {
		this.oddValue = oddValue;
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
		destinationPrediction.setOddValue(sourcePrediction.getOddValue());
		destinationPrediction.setPrediction(sourcePrediction.getPrediction());
		destinationPrediction.setResult(sourcePrediction.getResult());
	}

}
