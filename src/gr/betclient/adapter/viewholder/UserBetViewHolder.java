package gr.betclient.adapter.viewholder;

import android.widget.TextView;

public class UserBetViewHolder {
	
    private TextView betStatus;
    
    private TextView betPlaceDate;
    
    private TextView betAmount;
    
    private TextView betPossibleEarnings;

	public TextView getBetPlaceDate() {
		return betPlaceDate;
	}

	public void setBetPlaceDate(TextView betPlaceDate) {
		this.betPlaceDate = betPlaceDate;
	}

	public TextView getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(TextView betAmount) {
		this.betAmount = betAmount;
	}

	public TextView getBetPossibleEarnings() {
		return betPossibleEarnings;
	}

	public void setBetPossibleEarnings(TextView betPossibleEarnings) {
		this.betPossibleEarnings = betPossibleEarnings;
	}

	public TextView getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(TextView betStatus) {
		this.betStatus = betStatus;
	}
	
}
