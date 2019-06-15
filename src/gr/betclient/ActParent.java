package gr.betclient;

import gr.betclient.adapter.BottomPagerAdapter;
import gr.betclient.async.AsyncGetLeaderBoard;
import gr.betclient.async.AsyncGetLeagues;
import gr.betclient.async.AsyncHolder;
import gr.betclient.data.AppConstants;
import gr.betclient.frg.FrgAllEvents;
import gr.betclient.frg.FrgLeaderBoard;
import gr.betclient.frg.FrgLiveEvents;
import gr.betclient.frg.FrgMyBets;
import gr.betclient.frg.FrgUpcomingEvents;
import gr.betclient.model.event.Event;
import gr.betclient.model.event.League;
import gr.betclient.model.user.User;
import gr.betclient.model.user.UserBet;
import gr.betclient.pager.NonSwipeableViewPager;
import gr.betclient.util.DateUtils;
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
	Map<Integer, League> leaguesMap = new HashMap<Integer, League>();
	
	Map<Integer, League> leaguesWithUpcomingEventsMap = new HashMap<Integer, League>();
	
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
	        	new AsyncGetLeagues(holder).execute();
	        }
	    };
	    Timer timer = new Timer("Timer");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        setupPager();
        timer.scheduleAtFixedRate(task, 10, AppConstants.UPDATE_EVENTS_INTERVAL);


        
        new AsyncGetLeaderBoard(holder).execute();
        new AsyncGetLeagues(holder).execute();
        
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
       // mPager.setOffscreenPageLimit(2);
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
    
    /**
     * Clear the competitions, liveEvents upcoming events.
     * Re-enter with the ones received.
     * We ALWAYS have  to keep the same object reference for our adapters to work.
     */
    @SuppressWarnings("unchecked")
	@Override
	public void onAsyncEventsFinished(List<? extends Serializable> objectList) {
		leaguesMap.clear();
		liveEvents.clear();
		leaguesWithUpcomingEventsMap.clear();
		
		List<League> leagues = new ArrayList<League>();
			for (League league : (List<League>) objectList) {
				List<Event> events = league.getEvents();
					leagues.add(league);
					for (Event event : events) {
						if (event.isLive()) {
							liveEvents.add(event);
						}
					}
			}
		
		for (int i=0; i < leagues.size(); i++) {
			leaguesMap.put(i, leagues.get(i));
		}
		
		for (Map.Entry<Integer, League> entry : leaguesMap.entrySet()) {
    		leaguesWithUpcomingEventsMap.put(entry.getKey(), League.copyOf(entry.getValue()));
		}
		
		filterUpcoming(leaguesWithUpcomingEventsMap);
		
//		Map<String, Map<Integer, League>> competitionsByDate = splitEventsByDate(leaguesMap);
		
		updateFragments();
		
		
	}

	Map<String, Map<Integer, League>> splitEventsByDate(
			Map<Integer, League> competitionsMap2) {
		
		Map<String, Map<Integer, League>> splitEventsByDate = new HashMap<String, Map<Integer,League>>();
		for (int i=-3; i<3; i++){
			splitEventsByDate.put(DateUtils.getDate(i), new HashMap<Integer, League>());
		}
		
		
		return null;
	}

	private void updateFragments() {
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
			Map<Integer, League> competitionsWithUpcomingEventsMap2) {
		((BetClientApplication)getApplication()).getAllEventsMap().clear();
		for (Map.Entry<Integer, League> entry: new HashMap<Integer, League>(competitionsWithUpcomingEventsMap2).entrySet()){
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

	@SuppressWarnings("unchecked")
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

	public Map<Integer, League> getCompetitionsMap() {
		return leaguesMap;
	}
    
    public List<Event> getLiveEvents() {
		return liveEvents;
	}
    
    public Map<Integer, League> getCompetitionsWithUpcomingEventsMap() {
		return leaguesWithUpcomingEventsMap;
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
	
	@Override
	public void onAsyncCreateUserSuccess(User user) {
		FrgMyBets frgMyBets = (FrgMyBets)((BottomPagerAdapter) mPager.getAdapter()).getItem(4);
		frgMyBets.onUserCreated(user);
		Toast.makeText(this, "Welcome " + user.getUsername(), Toast.LENGTH_LONG).show();
	}
    
}
