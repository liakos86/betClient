package gr.betclient.frg;

import gr.betclient.ActParent;
import gr.betclient.R;
import gr.betclient.adapter.CompetitionsExpandableAdapterItem;
import gr.betclient.model.event.League;
import gr.betclient.model.event.Event;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Fragment that contains all the {@link League}s which have at least one {@link Event},
 * as they are fetched from server. It does not matter if they are live or not, everything will
 * appear here. 
 * 
 * TODO: when date is added as a parameter, only {@link League}s in the specific date will appear.
 */
public class FrgAllEvents extends Fragment {
	
 	/**
	 * Ui element to display all competitions.
	 */
 	ExpandableListView competitionsListView;

 	/**
 	 * Customer adapter for {@link League}s
 	 */
    CompetitionsExpandableAdapterItem competitionsListViewAdapter;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_all_events, container, false);
        setViews(v);
        return v;
    }

    void setViews(View v) {
    	 competitionsListView =  (ExpandableListView) v.findViewById(R.id.allEvents);
	     competitionsListViewAdapter = new CompetitionsExpandableAdapterItem(((ActParent)getActivity()).getCompetitionsMap(), this.getActivity());
	     competitionsListView.setAdapter(competitionsListViewAdapter);
    }
   
    public static FrgAllEvents init(int val) {
        FrgAllEvents frg = new FrgAllEvents();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        frg.setArguments(args);
        return frg;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

	public void updateEvents() {
		competitionsListViewAdapter.notifyDataSetChanged();
	}
	
}
