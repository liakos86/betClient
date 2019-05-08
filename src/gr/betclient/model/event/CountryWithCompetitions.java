package gr.betclient.model.event;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryWithCompetitions 
implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("country_id")
	@Expose
	String countryId;
	
	@SerializedName("country_name")
	@Expose
	String countryName;
	
	List<Competition> competitions;

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

	public List<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<Competition> competitions) {
		this.competitions = competitions;
	}

}
