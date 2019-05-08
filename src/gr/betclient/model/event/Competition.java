
package gr.betclient.model.event;



import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {

    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    
    private List<Event> events;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public static Competition copyOf(Competition competition){
		Competition newCompetition = new Competition();
		newCompetition.setCountryId(competition.getCountryId());
		newCompetition.setCountryName(competition.getCountryName());
		newCompetition.setLeagueId(competition.getLeagueId());
		newCompetition.setLeagueName(competition.getLeagueName());
		newCompetition.setEvents(new ArrayList<Event>(competition.getEvents()));
		return newCompetition;
	}

}
