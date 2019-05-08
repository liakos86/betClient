package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.act.ActParent;
import gr.betclient.adapter.viewholder.LeaderboardUserRowViewHolder;
import gr.betclient.adapter.viewholder.UserPredictionViewHolder;
import gr.betclient.model.user.User;
import gr.betclient.model.user.UserPrediction;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UserPredictionsListViewAdapterItem 
extends ArrayAdapter<UserPrediction> {

    int layoutResourceId;
    List<UserPrediction> data;
    Activity act;

    public UserPredictionsListViewAdapterItem(Activity act, int layoutResourceId,
            List<UserPrediction> data) {
        super(act.getApplicationContext(), layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.act = act;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        UserPredictionViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof UserPredictionViewHolder)) {
            convertView = act.getLayoutInflater().inflate(R.layout.list_user_prediction_row, parent, false);

            holder = new UserPredictionViewHolder();

            holder.setHomeTeamName((TextView) convertView
                    .findViewById(R.id.prediction_home_team_name));
            holder.setAwayTeamName((TextView) convertView
                    .findViewById(R.id.prediction_home_team_name));
            holder.setPrediction((TextView) convertView
                    .findViewById(R.id.prediction_user));
            convertView.setTag(holder);
        } else {
            holder = (UserPredictionViewHolder) convertView.getTag();

        }

        UserPrediction currentUser = data.get(position);

        holder.getHomeTeamName().setText("team1   vs");
 
        holder.getAwayTeamName().setText(" team2");
       
        holder.getPrediction().setText(currentUser.getPrediction());
       
        return convertView;
    }
    
}

