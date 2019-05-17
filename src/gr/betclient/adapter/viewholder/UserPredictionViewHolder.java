package gr.betclient.adapter.viewholder;

import android.widget.TextView;

public class UserPredictionViewHolder extends LiveEventViewHolder{
	
    private TextView predictionDescription;
    
    private TextView predictionSelection;

	public TextView getPredictionSelection() {
		return predictionSelection;
	}

	public void setPredictionSelection(TextView predictionSelection) {
		this.predictionSelection = predictionSelection;
	}

	public TextView getPredictionDescription() {
		return predictionDescription;
	}

	public void setPredictionDescription(TextView predictionDescription) {
		this.predictionDescription = predictionDescription;
	}
	
}
