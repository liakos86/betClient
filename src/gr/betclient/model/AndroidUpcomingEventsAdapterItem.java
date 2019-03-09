package gr.betclient.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gr.betclient.R;

import java.util.List;

public class AndroidUpcomingEventsAdapterItem extends ArrayAdapter<AndroidUpcomingEvent> {

    Context mContext;
    int layoutResourceId;
    List<AndroidUpcomingEvent> data;
    Activity activity;

    public AndroidUpcomingEventsAdapterItem(Activity activity, Context mContext, int layoutResourceId,
                                     List<AndroidUpcomingEvent> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        intervalViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof intervalViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_upcoming_event_row, parent, false);

            holder = new intervalViewHolder();

            holder.team1 = (TextView) convertView
                    .findViewById(R.id.team2);
            holder.team2 = (TextView) convertView
                    .findViewById(R.id.team1);
            convertView.setTag(holder);
        } else {
            holder = (intervalViewHolder) convertView.getTag();

        }

     //   convertView.setBackgroundColor(mContext.getResources().getColor(Color.DKGRAY));
        AndroidUpcomingEvent current = data.get(position);

        holder.team1.setText(current.getHomeTeam() +" vs "+current.getTeams().get(1));
        holder.team2.setText("1: "+current.getOdds().get(0) +" X: "+ current.getOdds().get(2)+ "2: "+current.getOdds().get(1));
        return convertView;
    }

    private class intervalViewHolder {
        TextView team1;
        TextView team2;
    }
}

