package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.CompetitionViewHolder;
import gr.betclient.adapter.viewholder.LiveEventViewHolder;
import gr.betclient.model.event.Competition;
import gr.betclient.model.event.Event;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class CompetitionsExpandableAdapterItem extends BaseExpandableListAdapter {
    private Activity activity;
    private Map<Integer, Competition> parentItems;
    private LayoutInflater inflater;

    public CompetitionsExpandableAdapterItem(Map<Integer, Competition> parentItems, Activity activity) {
        this.parentItems = parentItems;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    	List<Event> childEvents =  parentItems.get(groupPosition).getEvents();
        LiveEventViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof LiveEventViewHolder)) {
            convertView = inflater.inflate(R.layout.list_live_event_row, parent, false);
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

        Event event = childEvents.get(childPosition);
        holder.getHomeTeamName().setText(event.getMatchHometeamName());
        holder.getHomeTeamScore().setText(event.getMatchHometeamScore());
        holder.getAwayTeamName().setText(event.getMatchAwayteamName());
        holder.getAwayTeamScore().setText(event.getMatchAwayteamScore());
        holder.getEventCurrentMinute().setText(event.getMatchTime()+"'");
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	CompetitionViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof CompetitionViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_group_competition_row, parent, false);
            holder = new CompetitionViewHolder();
            holder.setCountryWithLeagueName((TextView) convertView
                    .findViewById(R.id.country_and_league_name));
            holder.setNumberOfEvents((TextView) convertView
                    .findViewById(R.id.number_of_events));
            convertView.setTag(holder);
        } else {
            holder = (CompetitionViewHolder) convertView.getTag();

        }
        Competition current = parentItems.get(groupPosition);
        holder.getCountryWithLeagueName().setText(current.getCountryName() +  ": "+current.getLeagueName());
        holder.getNumberOfEvents().setText("[" + current.getEvents().size() +"]");
    
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (parentItems.entrySet().size() > 0) {
            return parentItems.get(groupPosition).getEvents().size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
