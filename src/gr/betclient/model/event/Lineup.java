
package gr.betclient.model.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lineup {

    @SerializedName("home")
    @Expose
    private Home home;
    @SerializedName("away")
    @Expose
    private Away away;

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Away getAway() {
        return away;
    }

    public void setAway(Away away) {
        this.away = away;
    }

}
