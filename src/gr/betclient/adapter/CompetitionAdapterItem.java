package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.model.event.League;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CompetitionAdapterItem extends ArrayAdapter<League> {

    Context mContext;
    int layoutResourceId;
    List<League> competitions;
    Activity activity;

    public CompetitionAdapterItem(Activity activity, int layoutResourceId,
                                     List<League> data) {
        super(activity.getApplicationContext(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.competitions = data;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        CountryWithCompetitionViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof CountryWithCompetitionViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_group_competition_row, parent, false);
            holder = new CountryWithCompetitionViewHolder();
            holder.countryWithLeagueName = (TextView) convertView
                    .findViewById(R.id.country_and_league_name);
            holder.numberOfEvents = (TextView) convertView
                    .findViewById(R.id.number_of_events);
            convertView.setTag(holder);
        } else {
            holder = (CountryWithCompetitionViewHolder) convertView.getTag();

        }

        final League current = competitions.get(position);
        holder.countryWithLeagueName.setText(current.getCountryName() +  ": "+current.getLeagueName());
        holder.numberOfEvents.setText("[" + current.getEvents().size() +"]");
    
        
        return convertView;
	}

    private class CountryWithCompetitionViewHolder {
        TextView countryWithLeagueName;
        TextView numberOfEvents;
    }
    
}
