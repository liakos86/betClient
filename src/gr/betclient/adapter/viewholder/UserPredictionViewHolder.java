package gr.betclient.adapter.viewholder;

import android.widget.TextView;

public class UserPredictionViewHolder extends LiveEventViewHolder {
	
    private TextView predictionStatus;
    
    private TextView prediction;
    
    

	public TextView getPrediction() {
		return prediction;
	}

	public void setPrediction(TextView prediction) {
		this.prediction = prediction;
	}

	public TextView getPredictionStatus() {
		return predictionStatus;
	}

	public void setPredictionStatus(TextView predictionStatus) {
		this.predictionStatus = predictionStatus;
	}
	
}
