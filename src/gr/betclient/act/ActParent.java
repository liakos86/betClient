package gr.betclient.act;

import gr.betclient.R;
import gr.betclient.adapter.BottomPagerAdapter;
import gr.betclient.async.AsyncGetCountriesWithCompetitions;
import gr.betclient.async.AsyncGetLeaderBoard;
import gr.betclient.async.AsyncHolder;
import gr.betclient.data.AppConstants;
import gr.betclient.frg.FrgAllEvents;
import gr.betclient.frg.FrgLeaderBoard;
import gr.betclient.frg.FrgLiveEvents;
import gr.betclient.frg.FrgMyBets;
import gr.betclient.frg.FrgUpcomingEvents;
import gr.betclient.model.event.Competition;
import gr.betclient.model.event.CountryWithCompetitions;
import gr.betclient.model.event.Event;
import gr.betclient.model.user.User;
import gr.betclient.model.user.UserBet;
import gr.betclient.pager.NonSwipeableViewPager;
import gr.betclient.util.EventUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * This parent activity acts as a container of all the fragments of the application.
 * Upon initialization it performs an async task which retrieves all the competitions along 
 * with their events. A timertask will then repeatedly fetch the updated competitions with events.
 */
@SuppressLint("UseSparseArrays")
public class ActParent 
extends BaseFrgActivityWithBottomButtons
implements AsyncHolder{

	/**
	 * Item to perform purchase of other {@link User}s' {@link UserBet}s
	 */
	PurchaseHolder purchaseHolder;
	
	/**
	 * All the competitions that {@link Event}s have been fetched for.
	 */
	Map<Integer, Competition> competitionsMap = new HashMap<Integer, Competition>();
	
	Map<Integer, Competition> competitionsWithUpcomingEventsMap = new HashMap<Integer, Competition>();
	
	/**
	 * All the {@link Event}s that have {@link Event#getMatchLive()} = '1'.
	 */
	List<Event> liveEvents = new ArrayList<Event>();
	
	List<User> leaderboardUsers = new ArrayList<User>();
	
    /**
     * A NonSwipeableViewPager that does not allow swiping
     */
    private NonSwipeableViewPager mPager;

    /**
     * The total size of the pager objects
     */
    static final int PAGER_SIZE = 5;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	final AsyncHolder holder = this;
    	TimerTask task = new TimerTask() {
	        public void run() {
	        	new AsyncGetCountriesWithCompetitions(holder).execute();
	        }
	    };
	    Timer timer = new Timer("Timer");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        setupPager();
        timer.scheduleAtFixedRate(task, 10, AppConstants.UPDATE_EVENTS_INTERVAL);


        
        new AsyncGetLeaderBoard(holder).execute();
        new AsyncGetCountriesWithCompetitions(holder).execute();
        
        purchaseHolder = new PurchaseHolder(this);
        
    }

    /**
     * Initializes the pager, sets adapter and listener
     * Sets the bottom buttons.
     *  
     * Fragments are not initialized upon {@link BottomPagerAdapter} creation 
     * so we need an offscreen page limit.
     * 
     */
    @SuppressWarnings("deprecation")
	private void setupPager() {
        mPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(new BottomPagerAdapter(getSupportFragmentManager(), PAGER_SIZE));
        mPager.setOffscreenPageLimit(4);
        setBottomButtons(mPager);
        setSelectedBottomButton(bottomButtons, 0);

        mPager.setOnPageChangeListener(new NonSwipeableViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int position) {
                //((BetClientApplication) getApplication()).setPosition(position);
                mPager.setCurrentItem(position);
                setSelectedBottomButton(bottomButtons, position);

                invalidateOptionsMenu();
            }

            public Fragment getActiveFragment(FragmentManager fragmentManager, int position) {
                final String name = makeFragmentName(mPager.getId(), position);
                final Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
                if (fragmentByTag == null) {
                    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    fragmentManager.dump("", null, new PrintWriter(outputStream, true), null);
                }
                return fragmentByTag;
            }

            private String makeFragmentName(int viewId, int index) {
                return "android:switcher:" + viewId + ":" + index;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    /**
     * Sets the state of the pressed button to 'selected'
     *
     * @param bottomButtons
     * @param position
     */
    private void setSelectedBottomButton(Map<Integer, Integer> bottomButtons, int position) {
        for (int key = 0; key < bottomButtons.size(); key++) {
            LinearLayout btn = (LinearLayout) findViewById(bottomButtons.get(key));
            btn.setSelected(key == position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void onAsyncEventsFinished(List<? extends Serializable> objectList) {
		competitionsMap.clear();
		liveEvents.clear();
		competitionsWithUpcomingEventsMap.clear();
		
		List<Competition> competitions = new ArrayList<Competition>();
		for (CountryWithCompetitions countryWithComp : (List<CountryWithCompetitions>) objectList) {
			List<Competition> competitions2 = countryWithComp.getCompetitions();
			for (Competition competition : competitions2) {
				List<Event> events = competition.getEvents();
				if (events.size() > 0) {
					competitions.add(competition);
					for (Event event : events) {
						if (Event.LIVE_INDICATION.equals(event.getMatchLive())) {
							liveEvents.add(event);
						}
					}
				}
			}
		}
		
		for (int i=0; i < competitions.size(); i++) {
			competitionsMap.put(i, competitions.get(i));
		}
		
		for (Map.Entry<Integer, Competition> entry : competitionsMap.entrySet()) {
    		competitionsWithUpcomingEventsMap.put(entry.getKey(), Competition.copyOf(entry.getValue()));
		}
		
		filterUpcoming(competitionsWithUpcomingEventsMap);
		
		FrgAllEvents frgEvents = (FrgAllEvents)((BottomPagerAdapter) mPager.getAdapter()).getItem(0);
		frgEvents.updateEvents();
		
		FrgLiveEvents frgLiveEvents = (FrgLiveEvents)((BottomPagerAdapter) mPager.getAdapter()).getItem(1);
		frgLiveEvents.updateLiveEvents();
		
		FrgUpcomingEvents frgUpcomingEvents = (FrgUpcomingEvents)((BottomPagerAdapter) mPager.getAdapter()).getItem(2);
		frgUpcomingEvents.updateUpcomingEvents();
		
		FrgMyBets frgMyBets = (FrgMyBets)((BottomPagerAdapter) mPager.getAdapter()).getItem(4);
		frgMyBets.updateUserBets();
	}

	void filterUpcoming(
			Map<Integer, Competition> competitionsWithUpcomingEventsMap2) {
		((BetClientApplication)getApplication()).getAllEventsMap().clear();
		for (Map.Entry<Integer, Competition> entry: new HashMap<Integer, Competition>(competitionsWithUpcomingEventsMap2).entrySet()){
			List<Event> events = entry.getValue().getEvents();
			for (Event event : new ArrayList<Event>(events)){
				((BetClientApplication)getApplication()).getAllEventsMap().put(event.getMatchId(), event);
				if (EventUtils.isInTheFuture(event)){
					continue;
				}
				events.remove(event);
			}
			
			if (events.size() == 0){
				competitionsWithUpcomingEventsMap2.remove(entry.getKey());
			}
		}
	}

	@Override
	public void onAsyncLeaderboardFinished(
			List<? extends Serializable> objectList) {
		if (objectList == null){
			return;
		}
		
		leaderboardUsers.clear();
		for (User user : (List<User>) objectList) {
			leaderboardUsers.add(user);
		}

		FrgLeaderBoard frgLeaderBoard = (FrgLeaderBoard)((BottomPagerAdapter) mPager.getAdapter()).getItem(3);
		frgLeaderBoard.updateLeaderBoard();
		
	}

	public Map<Integer, Competition> getCompetitionsMap() {
		return competitionsMap;
	}
    
    public List<Event> getLiveEvents() {
		return liveEvents;
	}
    
    public Map<Integer, Competition> getCompetitionsWithUpcomingEventsMap() {
		return competitionsWithUpcomingEventsMap;
	}

	public List<User> getLeaderboardUsers() {
		return leaderboardUsers;
	}

	public void confirmInAppPurchase(User currentUser) {

		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        alertDialogBuilder
	                .setMessage("This feature has a tiny fee. Proceed if you agree to support us.")
	                .setCancelable(false)
	                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                    }
	                })
	                .setNegativeButton("Proceed", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        purchaseHolder.onBillingAcceptedButtonClicked();
	                    }
	                });
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
		
	}

	@Override
	public void onAsyncPlaceBetFinished(UserBet userBet) {
		FrgUpcomingEvents frgUpcomingEvents = (FrgUpcomingEvents)((BottomPagerAdapter) mPager.getAdapter()).getItem(2);
		frgUpcomingEvents.clearPredictions();
		
		((BetClientApplication)getApplication()).getUser().getUserBets().add(userBet);
		FrgMyBets frgMyBets = (FrgMyBets)((BottomPagerAdapter) mPager.getAdapter()).getItem(4);
		frgMyBets.updateUserBets();
		Toast.makeText(this, userBet.getMongoUserId(), Toast.LENGTH_LONG).show();
	}

    
}
