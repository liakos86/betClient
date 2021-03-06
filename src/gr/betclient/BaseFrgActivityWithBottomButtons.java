package gr.betclient;


import gr.betclient.R;
import gr.betclient.data.AppConstants;
import gr.betclient.pager.NonSwipeableViewPager;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * A fragment activity with a bottom button layout
 */
public class BaseFrgActivityWithBottomButtons extends FragmentActivity {
    private static final String TAG = Thread.currentThread().getStackTrace()[2].getClassName();

    /**
     * Key represents the position of the button while value the layoutId of the button
     */
    Map<Integer, Integer> bottomButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Populates the hashmap of the buttons and assigns a listener to each one of them
     *
     * @param mPager
     */
    protected void setBottomButtons(NonSwipeableViewPager mPager) {
        bottomButtons = new HashMap<Integer, Integer>();
        bottomButtons.put(AppConstants.ALL_EVENTS_POSITION, R.id.btn_all_events);
        bottomButtons.put(AppConstants.LIVE_EVENTS_POSITION, R.id.btn_live_events);
        bottomButtons.put(AppConstants.UPCOMING_EVENTS_POSITION, R.id.btn_upcoming_events);
        bottomButtons.put(AppConstants.LEADERBOARD_POSITION, R.id.btn_leader_board);
        bottomButtons.put(AppConstants.MY_BETS_POSITION, R.id.btn_my_bets);
        for (int counter = 0; counter < ActParent.PAGER_SIZE; counter++) {
            setBottomButtonListener(mPager, bottomButtons.get(counter), counter);
        }
    }

    /**
     * Sets the global position of the bottom buttons and then starts the
     * appropriate fragment or the new interval activity
     *
     * @param mPager
     * @param position the position that the user selected
     */
    private void startMain(NonSwipeableViewPager mPager, int position) {
        if (null != mPager) {
            mPager.setCurrentItem(position);
        } else {
            startMainWhenNoPager(position);
        }
    }

    /**
     * Restarts the activity if the mPager is null.
     *
     * @param position
     */
    private void startMainWhenNoPager(int position) {
        Intent intent = new Intent(this, ActParent.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        finish();
    }



    /**
     * If the positions is 0 or 1 we just change the displayed fragment
     * If the position is 2 we start a new IntervalActivity
     *
     * @param mPager
     * @param btn      the xml id of the button
     * @param position the position of the button in the layout
     */
    private void setBottomButtonListener(final NonSwipeableViewPager mPager, int btn, final int position) {
        LinearLayout bottomButton = (LinearLayout) findViewById(btn);
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain(mPager, position);
            }

        });
    }
}
