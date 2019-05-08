package gr.betclient.frg;

import java.util.ArrayList;
import java.util.List;

import gr.betclient.R;
import gr.betclient.act.ActParent;
import gr.betclient.act.BetClientApplication;
import gr.betclient.adapter.CompetitionsUpcomingExpandableAdapterItem;
import gr.betclient.adapter.UserPredictionsListViewAdapterItem;
import gr.betclient.async.AsyncPlaceBet;
import gr.betclient.model.event.Competition;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.UserBet;
import gr.betclient.model.user.UserPrediction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Fragment that contains all the {@link Competition}s which have at least one {@link Event},
 * that start later than the current time. This serves the purpose of betting on upcoming events
 * only.
 * 
 * TODO: when date is added as a parameter, only {@link Competition}s in the specific date will appear.
 */
public class FrgUpcomingEvents extends Fragment {
	
 	/**
	 * Ui element to display all competitions.
	 */
 	ExpandableListView competitionsListView;

 	/**
 	 * Customer adapter for {@link Competition}s
 	 */
    CompetitionsUpcomingExpandableAdapterItem competitionsListViewAdapter;
    
    ListView userPredictionsListView;
    
    UserPredictionsListViewAdapterItem userPredictionsListViewAdapterItem;
    
    Button buttonPlaceBet;
    
    RelativeLayout betSlipLayout;
    
    List<UserPrediction> userPredictionsList;
    
    UserBet userBet = new UserBet();
    
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
	        
//	        betSlipLayout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.layout_bet_slip, null);
			//competitionsListView.addFooterView(betSlipLayout);
	        
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
   
    void placeBet() {
		userBet.setBetAmount(50);
		userBet.setUserId(((BetClientApplication)getActivity().getApplication()).getUser().getId());
		new AsyncPlaceBet(userBet, (ActParent)this.getActivity()).execute();
		
		userBet = UserBet.clear(userBet);
		
	}
    
    public void addPrediction(UserPrediction prediction){
    	
    	betSlipLayout.setVisibility(View.VISIBLE);
    	userBet.getPredictions().add(prediction);
    	
    	userPredictionsListViewAdapterItem.notifyDataSetChanged();
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

	public void removePrediction(UserPrediction prediction) {

		for (UserPrediction existingPrediction : new ArrayList<UserPrediction>(userBet.getPredictions())){
			if (existingPrediction.getEventId().equals(prediction.getEventId()) && existingPrediction.getPrediction().equals(prediction.getPrediction())){
				userBet.getPredictions().remove(existingPrediction);
			}
		}
		
    	
    	userPredictionsListViewAdapterItem.notifyDataSetChanged();
		if (userBet.getPredictions().size() ==0){
			betSlipLayout.setVisibility(View.GONE);
		}
	}
}
