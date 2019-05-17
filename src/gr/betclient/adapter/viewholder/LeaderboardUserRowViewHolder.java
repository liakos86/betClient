package gr.betclient.adapter.viewholder;

import android.widget.ImageButton;
import android.widget.TextView;

public class LeaderboardUserRowViewHolder {
    private TextView textUserName;
    private TextView textUserScore;
    private TextView textUserPercentage;
    private ImageButton  buttonBuyPrediction;
	public TextView getTextUserName() {
		return textUserName;
	}
	public void setTextUserName(TextView textUserName) {
		this.textUserName = textUserName;
	}
	public TextView getTextUserScore() {
		return textUserScore;
	}
	public void setTextUserScore(TextView textUserScore) {
		this.textUserScore = textUserScore;
	}
	public ImageButton getButtonBuyPrediction() {
		return buttonBuyPrediction;
	}
	public void setButtonBuyPrediction(ImageButton buttonBuyPrediction) {
		this.buttonBuyPrediction = buttonBuyPrediction;
	}
	public TextView getTextUserPercentage() {
		return textUserPercentage;
	}
	public void setTextUserPercentage(TextView textUserPercentage) {
		this.textUserPercentage = textUserPercentage;
	}
	
    
}