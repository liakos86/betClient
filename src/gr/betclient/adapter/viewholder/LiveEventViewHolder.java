package gr.betclient.adapter.viewholder;

import android.widget.TextView;

public class LiveEventViewHolder {
	private TextView homeTeamName;
    private TextView awayTeamName;
    private TextView homeTeamScore;
    private TextView awayTeamScore;
    private TextView eventCurrentMinute;
	public TextView getHomeTeamName() {
		return homeTeamName;
	}
	public void setHomeTeamName(TextView homeTeamName) {
		this.homeTeamName = homeTeamName;
	}
	public TextView getHomeTeamScore() {
		return homeTeamScore;
	}
	public void setHomeTeamScore(TextView homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	public TextView getAwayTeamName() {
		return awayTeamName;
	}
	public void setAwayTeamName(TextView awayTeamName) {
		this.awayTeamName = awayTeamName;
	}
	public TextView getAwayTeamScore() {
		return awayTeamScore;
	}
	public void setAwayTeamScore(TextView awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}
	public TextView getEventCurrentMinute() {
		return eventCurrentMinute;
	}
	public void setEventCurrentMinute(TextView eventCurrentMinute) {
		this.eventCurrentMinute = eventCurrentMinute;
	}
}
