package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.act.ActParent;
import gr.betclient.adapter.viewholder.LeaderboardUserRowViewHolder;
import gr.betclient.model.user.User;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LeaderBoardUserAdapterItem 
extends ArrayAdapter<User> {

    int layoutResourceId;
    List<User> data;
    Activity act;

    public LeaderBoardUserAdapterItem(Activity act, int layoutResourceId,
            List<User> data) {
        super(act.getApplicationContext(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.act = act;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LeaderboardUserRowViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof LeaderboardUserRowViewHolder)) {
            convertView = act.getLayoutInflater().inflate(R.layout.list_leaderboard_user_row, parent, false);

            holder = new LeaderboardUserRowViewHolder();

            holder.setTextUserName((TextView) convertView
                    .findViewById(R.id.row_user_name));
            holder.setTextUserScore((TextView) convertView
                    .findViewById(R.id.row_user_score));
            holder.setButtonBuyPrediction((Button) convertView
                    .findViewById(R.id.row_buy_prediction));
            holder.setTextUserPercentage((TextView) convertView
                    .findViewById(R.id.row_user_percentage));
      
            convertView.setTag(holder);
        } else {
            holder = (LeaderboardUserRowViewHolder) convertView.getTag();

        }

        final User currentUser = data.get(position);

        holder.getTextUserName().setText(currentUser.getUsername());
 
        holder.getTextUserScore().setText(String.valueOf(currentUser.getBalance()));
        
        int totalSlips = currentUser.getLostSlipsCount()+currentUser.getWonSlipsCount();
        String percentageText = totalSlips > 0 ? String.format("%.1f",((float)currentUser.getWonSlipsCount()*100 / totalSlips))+"%" : "0%";
		holder.getTextUserPercentage().setText(percentageText);

        holder.getButtonBuyPrediction().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((ActParent)act).confirmInAppPurchase(currentUser);
			}
		});
        
        return convertView;
    }
    
}

