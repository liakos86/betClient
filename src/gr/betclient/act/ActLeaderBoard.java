package gr.betclient.act;

import gr.betclient.R;
import gr.betclient.async.AsyncHolder;
import gr.betclient.async.LoadAsyncLeaderBoard;
import gr.betclient.model.user.LeaderBoardUserAdapterItem;
import gr.betclient.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ListView;

import com.android.vending.util.IabHelper;
import com.android.vending.util.IabResult;
import com.android.vending.util.Inventory;
import com.android.vending.util.Purchase;

public class ActLeaderBoard 
extends Activity
implements AsyncHolder {
	
	Button buttonPurchasePrediction;
	
	ListView listViewLeaderboard;

    LeaderBoardUserAdapterItem leaderBoardUsersListViewAdapter;

    List<User> users = new ArrayList<User>();

    /*
     * 
     * ANDROID BILLING DATA
     * 
     */
    
    // Does the user have the premium upgrade?
    public boolean mIsPremium = false;

    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_MANUAL_TEXT = "manual_text_permission";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    
    // The helper object
    IabHelper mHelper;
    
    String payload;
    
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            //Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                //complain("Failed to query inventory: " + result);
                return;
            }


            //Toast.makeText(getApplication(), "Query inventory was successful. ", Toast.LENGTH_SHORT).show();

            // Log.d(TAG, "Query inventory was successful. ");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_MANUAL_TEXT);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            //Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

            //Toast.makeText(getApplication(), "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"), Toast.LENGTH_SHORT).show();

            // updateUi();
            // setWaitScreen(false);

            //Toast.makeText(getApplication(), "Initial inventory query finished; enabling main UI.", Toast.LENGTH_SHORT).show();

            // Log.d(TAG, );
        }
    };

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String returnedPayload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        return returnedPayload.equals(payload);
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            //complain("Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                //complain("Error purchasing: " + result);
                //setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                //complain("Error purchasing. Authenticity verification failed.");
                //setWaitScreen(false);
                return;
            }

            //complain( "Purchase successful.");

            if (purchase.getSku().equals(SKU_MANUAL_TEXT)) {
                // bought the premium upgrade!
                //complain("Purchase is premium upgrade. Congratulating user.");
                //alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                //updateUi();
                //setWaitScreen(false);
            }

        }
    };

    // User clicked the "Upgrade to Premium" button.
    public void onBillingAcceptedButtonClicked() {
        //complain("Upgrade button clicked; launching purchase flow for upgrade.");
        //setWaitScreen(true);

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
//        payload = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        mHelper.launchPurchaseFlow(this, SKU_MANUAL_TEXT, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }
    
    
    
    /*
     * 
     * END OF BILLING
     * 
     */
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //complain("onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            //complain("onActivityResult handled by IABUtil.");
        }
    }
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        
        setupViews();
        setupListeners();
      //  setupAppBilling();

        new LoadAsyncLeaderBoard(this).execute();

    }
    
    
    private void setupListeners() {
		// TODO Auto-generated method stub
		
	}



	private void setupAppBilling() {
    	 payload = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//         FacebookSdk.sdkInitialize(getApplicationContext());

         // load game data
         //loadData();

         String base64EncodedPublicKey = null;// getResources().getString(R.string.api_google_key);

         // Create the helper, passing it our context and the public key to verify signatures with
         //Toast.makeText(getApplication(),  "Creating IAB helper", Toast.LENGTH_SHORT).show();
         mHelper = new IabHelper(this, base64EncodedPublicKey);

         // enable debug logging (for a production application, you should set this to false).
         mHelper.enableDebugLogging(true);

         // Start setup. This is asynchronous and the specified listener
         // will be called once setup completes.
         //Toast.makeText(getApplication(),   "Starting setup", Toast.LENGTH_SHORT).show();
         mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
             public void onIabSetupFinished(IabResult result) {
                 //Toast.makeText(getApplication(),  "Setup finished.", Toast.LENGTH_SHORT).show();

                 if (!result.isSuccess()) {
                     // Oh noes, there was a problem.
                     //complain("Problem setting up in-app billing: " + result);
                     return;
                 }

                 // Have we been disposed of in the meantime? If so, quit.
                 if (mHelper == null) return;

                 // IAB is fully set up. Now, let's get an inventory of stuff we own.
                 //complain("Setup successful. Querying inventory.");
                 mHelper.queryInventoryAsync(mGotInventoryListener);
             }
         });
		
	}
	
	void setupViews() {
		
		users = new ArrayList<User>();
        listViewLeaderboard =  (ListView) findViewById(R.id.listview_users);
        leaderBoardUsersListViewAdapter = new LeaderBoardUserAdapterItem(this, this.getApplicationContext(),
                R.layout.list_leaderboard_user_row, users);
        listViewLeaderboard.setAdapter(leaderBoardUsersListViewAdapter);
        

        
		
	}
	
	 void confirmInAppPurchase() {
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
	                        proceedToBillingSetup();
	                    }
	                });
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
	}
	
	void proceedToBillingSetup() {
        //((ActMain) getActivity()).onBillingAcceptedButtonClicked();
    }



	@SuppressWarnings("unchecked")
	@Override
	public void onAsyncFinished(List<? extends Serializable> objectList) {
		
		users.clear();
		for (User user : (List<User>) objectList) {
			users.add(user);
		}

		leaderBoardUsersListViewAdapter.notifyDataSetChanged();
		
	}

}
