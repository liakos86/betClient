package gr.betclient.frg;

import gr.betclient.R;
import gr.betclient.act.ActParent;
import gr.betclient.adapter.BottomPagerAdapter;
import gr.betclient.adapter.LeaderBoardUserAdapterItem;
import gr.betclient.model.user.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * This fragment retrieves the top10 leaderboard {@link User}s.
 * Every row displays a 'bounty' button which gives the opportunity to purchase
 * each {@link User}'s forecoming predictions.
 */
public class FrgLeaderBoard extends Fragment {
	
	/**
	 * Ui element to display {@link User}s classification.
	 */
 	ListView leaderboardListView;

 	/**
 	 * Custom adapter for the leaderboard {@link User}s row.
 	 */
    LeaderBoardUserAdapterItem leaderboardListViewAdapterItem;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_leader_board, container, false);
        setViews(v);
        return v;
    }

	void setViews(View v) {
		leaderboardListView = (ListView) v.findViewById(R.id.listview_users);
		leaderboardListViewAdapterItem = new LeaderBoardUserAdapterItem(
				this.getActivity(), R.layout.list_leaderboard_user_row,
				((ActParent) this.getActivity()).getLeaderboardUsers());
		leaderboardListView.setAdapter(leaderboardListViewAdapterItem);
	}

   
    public static FrgLeaderBoard init(int val) {
        FrgLeaderBoard truitonList = new FrgLeaderBoard();

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
    
    

	public void updateLeaderBoard() {
		if (leaderboardListViewAdapterItem == null){
			return;
		}
		leaderboardListViewAdapterItem.notifyDataSetChanged();
	}
}
