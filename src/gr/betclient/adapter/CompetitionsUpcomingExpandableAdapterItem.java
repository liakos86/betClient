package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.UpcomingEventViewHolder;
import gr.betclient.frg.FrgUpcomingEvents;
import gr.betclient.model.event.Competition;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.UserPrediction;

import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class CompetitionsUpcomingExpandableAdapterItem extends BaseExpandableListAdapter {
    private Fragment fragment;
    private Map<Integer, Competition> parentItems;

    public CompetitionsUpcomingExpandableAdapterItem(Map<Integer, Competition> parentItems, Fragment fragment) {
        this.parentItems = parentItems;
        this.fragment = fragment;
    }

    private class CompetitionViewHolder {
    	TextView countryWithLeagueName;
        TextView numberOfEvents;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    	List<Event> childEvents =  parentItems.get(groupPosition).getEvents();
    	 UpcomingEventViewHolder holder = null;
         if (convertView == null || !(convertView.getTag() instanceof UpcomingEventViewHolder)) {
             convertView = fragment.getActivity().getLayoutInflater().inflate(R.layout.list_upcoming_event_row, parent, false);
             holder = new UpcomingEventViewHolder();
             holder.setCheckHome((CheckBox) convertView.findViewById(R.id.buttonCheckHome));
             holder.setCheckDraw((CheckBox) convertView.findViewById(R.id.buttonCheckDraw));
             holder.setCheckAway((CheckBox) convertView.findViewById(R.id.buttonCheckAway));
             convertView.setTag(holder);
         } else {
             holder = (UpcomingEventViewHolder) convertView.getTag();

         }

        Event current = childEvents.get(childPosition);
        holder.getCheckHome().setText(current.getMatchHometeamName() + "   " + current.getOdd().getOdd1());
        holder.getCheckDraw().setText("X "+current.getOdd().getOddX());
        holder.getCheckAway().setText(current.getMatchAwayteamName() + "   " + current.getOdd().getOdd2());
        
        
        holder.getCheckDraw().setOnCheckedChangeListener(onCheckChangeListener(holder, current, "X", current.getOdd().getOddX()));
        holder.getCheckHome().setOnCheckedChangeListener(onCheckChangeListener(holder, current, "1", current.getOdd().getOdd1()));
        holder.getCheckAway().setOnCheckedChangeListener(onCheckChangeListener(holder, current, "2", current.getOdd().getOdd2()));
        
        return convertView;
    }


	private OnCheckedChangeListener onCheckChangeListener(final UpcomingEventViewHolder holder, final Event event, final String predictionString, final String odd) {
		return new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				UserPrediction prediction = new UserPrediction();
				prediction.setEventId(event.getMatchId());
				prediction.setOddValue(Double.valueOf(odd));
				prediction.setPrediction(predictionString);
				prediction.setPredictionDescription(event.getMatchHometeamName()+ " - "+event.getMatchAwayteamName());
				if (buttonView.isChecked() && isChecked){
					((FrgUpcomingEvents)fragment).addPrediction(prediction, (CheckBox) buttonView);
					holder.clearOthers(buttonView.getId());
					return;
				}
				((FrgUpcomingEvents)fragment).removePrediction(prediction, (CheckBox) buttonView);
				
			}
		};
	}


	@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	CompetitionViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof CompetitionViewHolder)) {
            convertView = fragment.getActivity().getLayoutInflater().inflate(R.layout.list_group_competition_row, parent, false);
            holder = new CompetitionViewHolder();
            holder.countryWithLeagueName = (TextView) convertView
                    .findViewById(R.id.country_and_league_name);
            holder.numberOfEvents = (TextView) convertView
                    .findViewById(R.id.number_of_events);
            convertView.setTag(holder);
        } else {
            holder = (CompetitionViewHolder) convertView.getTag();

        }
        Competition current = parentItems.get(groupPosition);
        holder.countryWithLeagueName.setText(current.getCountryName() +  ": "+current.getLeagueName());
        holder.numberOfEvents.setText("[" + current.getEvents().size() +"]");
    
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
