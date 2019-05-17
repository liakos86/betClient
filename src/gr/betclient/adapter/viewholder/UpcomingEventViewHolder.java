package gr.betclient.adapter.viewholder;

import android.widget.CheckBox;

public class UpcomingEventViewHolder {
	
	CheckBox checkHome;
	CheckBox checkDraw;
	CheckBox checkAway;
	
	
	public CheckBox getCheckHome() {
		return checkHome;
	}
	public void setCheckHome(CheckBox radioHome) {
		this.checkHome = radioHome;
	}
	public CheckBox getCheckDraw() {
		return checkDraw;
	}
	public void setCheckDraw(CheckBox radioDraw) {
		this.checkDraw = radioDraw;
	}
	public CheckBox getCheckAway() {
		return checkAway;
	}
	public void setCheckAway(CheckBox radioAway) {
		this.checkAway = radioAway;
	}
	
	public void clearOthers(int exceptForId){
		if (checkAway.getId() != exceptForId){
			checkAway.setChecked(false);
		}
		if (checkHome.getId() != exceptForId){
			checkHome.setChecked(false);
		}
		if (checkDraw.getId() != exceptForId){
			checkDraw.setChecked(false);
		}
	}
	
	

//	private TextView homeTeamName;
//	private TextView awayTeamName;
//	private TextView homeTeamOdd;
//	private TextView awayTeamOdd;
//	private TextView eventOddDraw
//	;
//	public TextView getHomeTeamName() {
//		return homeTeamName;
//	}
//	public void setHomeTeamName(TextView homeTeamName) {
//		this.homeTeamName = homeTeamName;
//	}
//	public TextView getAwayTeamName() {
//		return awayTeamName;
//	}
//	public void setAwayTeamName(TextView awayTeamName) {
//		this.awayTeamName = awayTeamName;
//	}
//	public void setAwayTeamOdd(TextView awayTeamOdd) {
//		this.awayTeamOdd = awayTeamOdd;
//	}
//	public void setHomeTeamOdd(TextView homeTeamOdd) {
//		this.homeTeamOdd = homeTeamOdd;
//	}
//	
//	public TextView getHomeTeamOdd() {
//		return homeTeamOdd;
//	}
//	
//	public TextView getAwayTeamOdd() {
//		return awayTeamOdd;
//	}
//	public TextView getEventOddDraw() {
//		return eventOddDraw;
//	}
//	public void setEventOddDraw(TextView eventOddDraw) {
//		this.eventOddDraw = eventOddDraw;
//	}
	
	

}
