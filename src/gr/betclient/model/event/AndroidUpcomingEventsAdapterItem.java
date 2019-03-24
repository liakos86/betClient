package gr.betclient.model.event;

import gr.betclient.R;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        intervalViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof intervalViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_upcoming_event_row, parent, false);

            holder = new intervalViewHolder();

            holder.home_team_name = (CheckBox) convertView
                    .findViewById(R.id.home_team_name);
            holder.away_team_name = (CheckBox) convertView
                    .findViewById(R.id.away_team_name);
            holder.draw_name = (CheckBox) convertView
                    .findViewById(R.id.draw_name);
      
            convertView.setTag(holder);
        } else {
            holder = (intervalViewHolder) convertView.getTag();

        }

        AndroidUpcomingEvent current = data.get(position);

        holder.home_team_name.setText(current.getTeams().get(0) +  ": "+current.getOdds().get(0));

        holder.draw_name.setText("X: "+ current.getOdds().get(2));
 
        holder.away_team_name.setText(current.getTeams().get(1) + ": "+current.getOdds().get(1));

        holder.home_team_name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fixCheckBoxes(v, parent);
			}

			
		});
        
        return convertView;
    }
    
    private void fixCheckBoxes(View v, View viewParent) {
		
//    	System.out.println(v.getId() +"    fffffff     "+viewParent.getId());
//    	v.setSelected(!v.isSelected());
    	
    	switch (v.getId()){
    	
    	case R.id.home_team_name :
    		viewParent.findViewById(R.id.draw_name).setSelected(false);
    		viewParent.findViewById(R.id.away_team_name).setSelected(false);
    		break;
    		
    	case R.id.draw_name :
    		viewParent.findViewById(R.id.home_team_name).setSelected(false);
    		viewParent.findViewById(R.id.away_team_name).setSelected(false);
    		break;
    		
    	case R.id.away_team_name :
    		viewParent.findViewById(R.id.draw_name).setSelected(false);
    		viewParent.findViewById(R.id.home_team_name).setSelected(false);
    		break;
    	
    	default:
    		
    	}
    	
		
		
	}

    private class intervalViewHolder {
    	
    	 CheckBox home_team_name;
    	 CheckBox away_team_name;
    	 CheckBox draw_name;
    	
    	
    	
//        TextView home_team_name;
//        TextView away_team_name;
        //TextView draw_name;
        
//        TextView home_team_odd;
//        TextView away_team_odd;
//        TextView draw_odd;
    }
    
    
    
}

