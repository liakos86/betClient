package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.UserBetViewHolder;
import gr.betclient.adapter.viewholder.UserPredictionViewHolder;
import gr.betclient.model.user.UserBet;
import gr.betclient.model.user.UserPrediction;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class UserBetsExpandableAdapterItem extends BaseExpandableListAdapter {
    private Activity activity;
    private List<UserBet> parentItems;
    private LayoutInflater inflater;

    public UserBetsExpandableAdapterItem(List<UserBet> parentItems, Activity activity) {
        this.parentItems = parentItems;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    	List<UserPrediction> childEvents =  parentItems.get(groupPosition).getPredictions();
        UserPredictionViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof UserPredictionViewHolder)) {
            convertView = inflater.inflate(R.layout.list_user_prediction_row, parent, false);
            holder = new UserPredictionViewHolder();
            holder.setPredictionDescription(((TextView) convertView.findViewById(R.id.prediction_description)));
            holder.setPredictionSelection(((TextView) convertView.findViewById(R.id.prediction_selection)));
            holder.setEventCurrentMinute((TextView) convertView.findViewById(R.id.prediction_event_current_minute));
            convertView.setTag(holder);
        } else {
            holder = (UserPredictionViewHolder) convertView.getTag();
        }

        UserPrediction prediction = childEvents.get(childPosition);
        holder.getPredictionDescription().setText(prediction.getPredictionDescription());
        holder.getPredictionSelection().setText(prediction.getPrediction() + "@" +prediction.getOddValue());
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	UserBetViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof UserBetViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_group_user_bet_row, parent, false);
            holder = new UserBetViewHolder();
            holder.setBetStatus((TextView) convertView.findViewById(R.id.betStatus));
            holder.setBetPossibleEarnings((TextView) convertView.findViewById(R.id.betPossibleEarnings));
            convertView.setTag(holder);
        } else {
            holder = (UserBetViewHolder) convertView.getTag();

        }
        UserBet current = parentItems.get(groupPosition);
        Double possibleEarnings = current.getPossibleEarnings();
        holder.getBetStatus().setText(current.getBetStatus() +" placed on "+current.getBetPlaceDate());
        holder.getBetPossibleEarnings().setText(possibleEarnings.toString());
    
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
        if (parentItems.size() > 0) {
            int size = parentItems.get(groupPosition).getPredictions().size();
			return size;
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
