package gr.betclient.frg;

import gr.betclient.ActParent;
import gr.betclient.BetClientApplication;
import gr.betclient.R;
import gr.betclient.adapter.CompetitionsUpcomingExpandableAdapterItem;
import gr.betclient.adapter.UserPredictionsListViewAdapterItem;
import gr.betclient.async.AsyncPlaceBet;
import gr.betclient.data.AppConstants;
import gr.betclient.model.event.League;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.User;
import gr.betclient.model.user.UserBet;
import gr.betclient.model.user.UserPrediction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Fragment that contains all the {@link League}s which have at least one {@link Event},
 * that start later than the current time. This serves the purpose of betting on upcoming events
 * only.
 * 
 * TODO: when date is added as a parameter, only {@link League}s in the specific date will appear.
 */
public class FrgUpcomingEvents extends Fragment {
	
 	/**
	 * Ui element to display all competitions.
	 */
 	ExpandableListView competitionsListView;

 	/**
 	 * Customer adapter for {@link League}s
 	 */
    CompetitionsUpcomingExpandableAdapterItem competitionsListViewAdapter;
    
    ListView userPredictionsListView;
    
    UserPredictionsListViewAdapterItem userPredictionsListViewAdapterItem;
    
    Button buttonPlaceBet;
    
    RelativeLayout betSlipLayout;
    
    List<UserPrediction> userPredictionsList;
    
    UserBet userBet = new UserBet();
    
    /**
     * After user has placed a bet we need to uncheck every checkbox.
     */
    Set<CheckBox> checkedBoxes = new HashSet<CheckBox>();
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_upcoming_events, container, false);
        setViews(v);
        return v;
    }

    /**
     * ONgroupClickListener prevents collapsing.
     * 
     * @param v
     */
    private void setViews(View v) {
    	 competitionsListView =  (ExpandableListView) v.findViewById(R.id.upComingEvents);
	        competitionsListViewAdapter = new CompetitionsUpcomingExpandableAdapterItem(((ActParent)this.getActivity()).getCompetitionsWithUpcomingEventsMap(), this);
	        competitionsListView.setAdapter(competitionsListViewAdapter);
	        competitionsListView.setOnGroupClickListener(new OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					return true;
				}
			});
	        
	        betSlipLayout = (RelativeLayout)v.findViewById(R.id.betSlipLayout);
	        buttonPlaceBet = (Button) betSlipLayout.findViewById(R.id.buttonPlaceBet);
	        buttonPlaceBet.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					placeBet();
				}
			});
	        
	        userPredictionsListView = (ListView)betSlipLayout.findViewById(R.id.betSlipPredictions);
	        userPredictionsListViewAdapterItem = new UserPredictionsListViewAdapterItem(getActivity(), R.layout.list_user_prediction_row, userBet.getPredictions());
	        userPredictionsListView.setAdapter(userPredictionsListViewAdapterItem);
	        
    }
   /**
    * Places a {@link UserBet} asynchronously.
    * Then it adds the new bet in the {@link User#getUserBets()} in order to appear in the bets frg.
    */
    void placeBet() {
    	
    	if (userBet.getPredictions().size() == 0){
    		return;
    	}
    	
    	Set<String> selectedEvents = new HashSet<String>();
    	for(UserPrediction prediction : userBet.getPredictions()){
    		if (selectedEvents.contains(prediction.getEventId())){
    			Toast.makeText(getActivity(), "You can only selected one event peodiction", Toast.LENGTH_LONG).show();
    			return;
    		}
    		selectedEvents.add(prediction.getEventId());
    	}
    	
    	SimpleDateFormat df = new SimpleDateFormat(AppConstants.EVENT_DATE_FORMAT);
		userBet.setBetAmount(50);
		userBet.setBetPlaceDate(df.format(new Date()));
		userBet.setMongoUserId(((BetClientApplication)getActivity().getApplication()).getUser().getMongoId());
		userBet.setBetStatus("open");
		new AsyncPlaceBet(userBet, (ActParent)this.getActivity()).execute();
		
		
	}
    
    /**
     * Adds a {@link UserPrediction} to the {@link UserBet}.
     * In case the same prediction exists already for the same {@link Event}, nothing is performed.
     * 
     * @param prediction
     */
    public void addPrediction(UserPrediction prediction, CheckBox checkBoxToAdd){
    	for (UserPrediction existingPrediction : new ArrayList<UserPrediction>(userBet.getPredictions())){
    		if (existingPrediction.getEventId().equals(prediction.getEventId()) && existingPrediction.getPrediction().equals(prediction.getPrediction())){
    			return;
    		}
    	}
    	betSlipLayout.setVisibility(View.VISIBLE);
    	userBet.getPredictions().add(prediction);
    	userPredictionsListViewAdapterItem.notifyDataSetChanged();
    	checkedBoxes.add(checkBoxToAdd);
	}
	
    public void removePrediction(UserPrediction prediction, CheckBox checkBoxToRemove) {
    	
    	if (userBet.getPredictions().size() == 0){//will be called by side effect when checkboxes are unchecked
    		return;
    	}
    	
    	for (UserPrediction existingPrediction : new ArrayList<UserPrediction>(userBet.getPredictions())){
    		if (existingPrediction.getEventId().equals(prediction.getEventId()) && existingPrediction.getPrediction().equals(prediction.getPrediction())){
    			userBet.getPredictions().remove(existingPrediction);
    		}
    	}
    	
    	userPredictionsListViewAdapterItem.notifyDataSetChanged();
    	if (userBet.getPredictions().size() ==0){
    		betSlipLayout.setVisibility(View.GONE);
    	}
    	
    	checkedBoxes.remove(checkBoxToRemove);
    }

	public static FrgUpcomingEvents init(int val) {
        FrgUpcomingEvents truitonList = new FrgUpcomingEvents();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonList.setArguments(args);
        return truitonList;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Expand all groups. Collapsing is disabled.
     */
	public void updateUpcomingEvents() {
		competitionsListViewAdapter.notifyDataSetChanged();
		for (int i=0; i<competitionsListViewAdapter.getGroupCount(); i++){
			competitionsListView.expandGroup(i);
		}
	}
	
	public void clearPredictions() {
		userBet = UserBet.clear(userBet);	
		for (CheckBox checkBox : checkedBoxes) {
			checkBox.setChecked(false);
		}
		checkedBoxes.clear();
		userPredictionsListViewAdapterItem.notifyDataSetChanged();

	}

}
