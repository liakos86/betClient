package gr.betclient.model.user;

import gr.betclient.R;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LeaderBoardUserAdapterItem 
extends ArrayAdapter<User> {

    Context mContext;
    int layoutResourceId;
    List<User> data;
    Activity activity;

    public LeaderBoardUserAdapterItem(Activity activity, Context mContext, int layoutResourceId,
                                     List<User> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        UserRowViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof UserRowViewHolder)) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_leaderboard_user_row, parent, false);

            holder = new UserRowViewHolder();

            holder.textUserName = (TextView) convertView
                    .findViewById(R.id.row_user_name);
            holder.textUserScore = (TextView) convertView
                    .findViewById(R.id.row_user_score);
            holder.buttonBuyPrediction = (Button) convertView
                    .findViewById(R.id.row_buy_prediction);
      
            convertView.setTag(holder);
        } else {
            holder = (UserRowViewHolder) convertView.getTag();

        }

        User currentUser = data.get(position);

        holder.textUserName.setText(currentUser.getUsername());
 
        holder.textUserScore.setText(String.valueOf(currentUser.getBalance()));

        holder.buttonBuyPrediction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}

			
		});
        
        return convertView;
    }
    
    private class UserRowViewHolder {
        TextView textUserName;
        TextView textUserScore;
        Button  buttonBuyPrediction;
        
    }
    
}

