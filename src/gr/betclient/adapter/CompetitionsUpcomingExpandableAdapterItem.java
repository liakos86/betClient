package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.UpcomingEventViewHolder;
import gr.betclient.frg.FrgMyBets;
import gr.betclient.frg.FrgUpcomingEvents;
import gr.betclient.model.event.Competition;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.UserPrediction;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
             holder.setRadioHome((RadioButton) convertView.findViewById(R.id.buttonRadioHome));
             holder.setRadioDraw((RadioButton) convertView.findViewById(R.id.buttonRadioDraw));
             holder.setRadioAway((RadioButton) convertView.findViewById(R.id.buttonRadioAway));
             convertView.setTag(holder);
         } else {
             holder = (UpcomingEventViewHolder) convertView.getTag();

         }

        Event current = childEvents.get(childPosition);
        holder.getRadioHome().setText(current.getMatchHometeamName() + "   " + current.getOdd().getOdd1());
        holder.getRadioDraw().setText("X "+current.getOdd().getOddX());
        holder.getRadioAway().setText(current.getMatchAwayteamName() + "   " + current.getOdd().getOdd2());
        
        
        holder.getRadioDraw().setOnTouchListener(touchListener(current.getMatchId(), "X", current.getOdd().getOddX()));
        holder.getRadioHome().setOnTouchListener(touchListener(current.getMatchId(), "1", current.getOdd().getOdd1()));
        holder.getRadioAway().setOnTouchListener(touchListener(current.getMatchId(), "2", current.getOdd().getOdd2()));
        
        return convertView;
    }


	private OnTouchListener touchListener(final String matchId, final String predictionString, final String odd) {
		return new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				UserPrediction prediction = new UserPrediction();
				prediction.setEventId(matchId);
				//prediction.setMultiplier(Double.valueOf(odd));
				prediction.setPrediction(predictionString);
				
				RadioButton b = (RadioButton) v;
				if (b.isChecked()){
					((FrgUpcomingEvents)fragment).removePrediction(prediction);
					((RadioGroup)b.getParent()).clearCheck();
					return true;
				}
				((FrgUpcomingEvents)fragment).addPrediction(prediction);
				return false;
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
