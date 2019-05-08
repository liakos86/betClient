package gr.betclient.adapter;

import gr.betclient.frg.FrgAllEvents;
import gr.betclient.frg.FrgLeaderBoard;
import gr.betclient.frg.FrgLiveEvents;
import gr.betclient.frg.FrgMyBets;
import gr.betclient.frg.FrgUpcomingEvents;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Pager adapter class.
 */
public class BottomPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;
    Activity activity;

    public BottomPagerAdapter(FragmentManager supportFragmentManager, int pageCount) {
        super(supportFragmentManager);
        fragments = new Fragment[pageCount];
        for (int i = 0; i < fragments.length; i++)
            fragments[i] = null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                if (fragments[position] == null) {
					fragments[position] = FrgAllEvents.init(0);
                }
                break;
            }
            case 1: {
                if (fragments[position] == null) {
                    fragments[position] = FrgLiveEvents.init(1);
                }
                break;
            }
            case 2: {
                if (fragments[position] == null) {
					fragments[position] = FrgUpcomingEvents.init(2);
                }
                break;
            }
            
            case 3: {
                if (fragments[position] == null) {
                    fragments[position] = FrgLeaderBoard.init(3);
                }
                break;
            }
            
            case 4: {
                if (fragments[position] == null) {
                    fragments[position] = FrgMyBets.init(4);
                }
                break;
            }
        }
        return fragments[position];
    }
    

}