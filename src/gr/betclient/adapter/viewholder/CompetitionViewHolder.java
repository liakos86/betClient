package gr.betclient.adapter.viewholder;

import android.widget.TextView;

public class CompetitionViewHolder {
	
	private TextView countryWithLeagueName;
    private TextView numberOfEvents;
	public TextView getCountryWithLeagueName() {
		return countryWithLeagueName;
	}
	public void setCountryWithLeagueName(TextView countryWithLeagueName) {
		this.countryWithLeagueName = countryWithLeagueName;
	}
	public TextView getNumberOfEvents() {
		return numberOfEvents;
	}
	public void setNumberOfEvents(TextView numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

}
