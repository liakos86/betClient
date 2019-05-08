package gr.betclient.frg;

import gr.betclient.R;
import gr.betclient.act.ActParent;
import gr.betclient.adapter.LiveEventAdapterItem;
import gr.betclient.model.event.Event;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Fragment that contains all the {@link Event}s with {@link Event#getMatchLive()} = '1' indication.
 * 
 */
public class FrgLiveEvents extends Fragment {
	
	/**
	 * Ui element to display live {@link Event}s.
	 */
 	ListView liveEventsView;

 	/**
 	 * Custom adapter for the live {@link Event}s row.
 	 */
    LiveEventAdapterItem liveEventsListViewAdapterItem;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.frg_live_events, container, false);
        setViews(v);
        return v;
    }

	private void setViews(View v) {
		liveEventsView = (ListView) v.findViewById(R.id.liveEvents);
		liveEventsListViewAdapterItem = new LiveEventAdapterItem(
				this.getActivity(), R.layout.list_live_event_row,
				((ActParent) this.getActivity()).getLiveEvents());
		liveEventsView.setAdapter(liveEventsListViewAdapterItem);
	}

   
    public static FrgLiveEvents init(int val) {
        FrgLiveEvents truitonList = new FrgLiveEvents();

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

	public void updateLiveEvents() {
		liveEventsListViewAdapterItem.notifyDataSetChanged();
	}
}
