package gr.betclient.adapter;

import gr.betclient.R;
import gr.betclient.adapter.viewholder.UserPredictionViewHolder;
import gr.betclient.model.user.UserPrediction;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            holder.setPredictionDescription((TextView) convertView
                    .findViewById(R.id.prediction_selection));
            holder.setPredictionSelection((TextView) convertView
                    .findViewById(R.id.prediction_description));
            convertView.setTag(holder);
        } else {
            holder = (UserPredictionViewHolder) convertView.getTag();

        }

        
        UserPrediction currentPrediction = data.get(position);
        holder.getPredictionDescription().setText(currentPrediction.getPredictionDescription());
        holder.getPredictionSelection().setText(currentPrediction.getPrediction() + " @ "+ currentPrediction.getOddValue());
       
        return convertView;
    }

}

