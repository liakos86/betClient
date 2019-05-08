package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.LiveEventViewHolder;
import gr.betclient.model.event.Event;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LiveEventAdapterItem extends ArrayAdapter<Event> {

    Context mContext;
    int layoutResourceId;
    List<Event> liveEvents;
    Activity activity;

    public LiveEventAdapterItem(Activity activity, int layoutResourceId,
                                     List<Event> data) {

        super(activity.getApplicationContext(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.liveEvents = data;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LiveEventViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof LiveEventViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_live_event_row, parent, false);
            holder = new LiveEventViewHolder();
            holder.setHomeTeamName((TextView) convertView.findViewById(R.id.home_team_name));
            holder.setHomeTeamScore((TextView) convertView.findViewById(R.id.home_team_score));
            holder.setAwayTeamName((TextView) convertView.findViewById(R.id.away_team_name));
            holder.setAwayTeamScore((TextView) convertView.findViewById(R.id.away_team_score));
            holder.setEventCurrentMinute((TextView) convertView.findViewById(R.id.event_current_minute));
            convertView.setTag(holder);
        } else {
            holder = (LiveEventViewHolder) convertView.getTag();

        }

        Event current = liveEvents.get(position);
        holder.getHomeTeamName().setText(current.getMatchHometeamName());
        holder.getHomeTeamScore().setText(current.getMatchHometeamScore());
        holder.getAwayTeamName().setText(current.getMatchAwayteamName());
        holder.getAwayTeamScore().setText(current.getMatchAwayteamScore());
        holder.getEventCurrentMinute().setText(current.getMatchTime()+"'");
        return convertView;
	}

}
