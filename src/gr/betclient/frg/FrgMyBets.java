package gr.betclient.frg;

import gr.betclient.ActParent;
import gr.betclient.BetClientApplication;
import gr.betclient.R;
import gr.betclient.adapter.UserBetsExpandableAdapterItem;
import gr.betclient.async.AsyncCreateUser;
import gr.betclient.data.AppConstants;
import gr.betclient.model.user.User;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ViewSwitcher;

/**
 * 
 */
public class FrgMyBets extends Fragment {
	
	ViewSwitcher switcher;
	
	/*
	 * registration fields.
	 * 
	 */
	
	EditText editTextUsername;
	
	Button buttonSaveUser;
	
	/*
	 * My bets fields.
	 * 
	 */
	
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
        
//        final SharedPreferences app_preferences = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
//		String userString = app_preferences.getString(AppConstants.PREFS_USER, null);
//		if (userString == null) {
//
//			Intent intent = new Intent(getActivity(), ActRegisterUser.class);
//	        startActivity(intent);
//			//finish()	
//			return;
//		}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_user_bets, container, false);
        setViews(v);
        
        final SharedPreferences app_preferences = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
 		String userString = app_preferences.getString(AppConstants.PREFS_USER, null);
 		if (userString == null) {
 			switcher.setDisplayedChild(1);
 			return v;
 		}
        
 		switcher.setDisplayedChild(0);
        return v;
    }
   
	void setViews(View v) {
		
		switcher = (ViewSwitcher) v.findViewById(R.id.switcher);
		
		editTextUsername = (EditText) v.findViewById(R.id.editTextUsername);
		buttonSaveUser = (Button) v.findViewById(R.id.buttonSaveUser);
		buttonSaveUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				User user = new User();
				user.setUsername(editTextUsername.getText().toString());
				AsyncCreateUser asyncCreateUser = new AsyncCreateUser(user,((BetClientApplication) getActivity().getApplication()));
				asyncCreateUser.setAsyncHolder(((ActParent)getActivity()));
				asyncCreateUser.execute();
			}
		});
		
		
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

	public void onUserCreated(User user) {
		switcher.setDisplayedChild(0);
	}

}
