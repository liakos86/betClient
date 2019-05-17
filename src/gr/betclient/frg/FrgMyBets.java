package gr.betclient.frg;

import gr.betclient.R;
import gr.betclient.act.ActRegisterUser;
import gr.betclient.act.BetClientApplication;
import gr.betclient.adapter.UserBetsExpandableAdapterItem;
import gr.betclient.data.AppConstants;
import gr.betclient.model.user.UserBet;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

/**
 * 
 */
public class FrgMyBets extends Fragment {
	
	/**
	 * Ui element to display {@link UserBet}s.
	 */
 	ExpandableListView userBetsExpListView;

 	/**
 	 * Custom adapter for the {@link UserBet}s row.
 	 */
    UserBetsExpandableAdapterItem userBetsListViewAdapterItem;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final SharedPreferences app_preferences = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
		String userString = app_preferences.getString(AppConstants.PREFS_USER, null);
		if (userString == null) {

			Intent intent = new Intent(getActivity(), ActRegisterUser.class);
	        startActivity(intent);
			//finish()	
			return;
		}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_user_bets, container, false);
        setViews(v);
        return v;
    }

	void setViews(View v) {
		userBetsExpListView = (ExpandableListView) v.findViewById(R.id.userBets);
		List<UserBet> bets = ((BetClientApplication)getActivity().getApplication()).getBets();
		userBetsListViewAdapterItem = new UserBetsExpandableAdapterItem(bets, this.getActivity());
		userBetsExpListView.setAdapter(userBetsListViewAdapterItem);
		userBetsExpListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
	}
	
	
    public static FrgMyBets init(int val) {
        FrgMyBets truitonList = new FrgMyBets();

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

	public void updateUserBets() {
		userBetsListViewAdapterItem.notifyDataSetChanged();
		for (int i=0; i<userBetsListViewAdapterItem.getGroupCount(); i++){
			userBetsExpListView.expandGroup(i);
		}
	}

}
