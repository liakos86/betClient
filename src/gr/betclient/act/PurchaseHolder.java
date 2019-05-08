package gr.betclient.act;

import gr.betclient.R;
import android.app.Activity;

import com.android.vending.util.IabHelper;
import com.android.vending.util.IabResult;
import com.android.vending.util.Inventory;
import com.android.vending.util.Purchase;

import android.provider.Settings;


public class PurchaseHolder {
	
	
	 /*
     * 
     * ANDROID BILLING DATA
     * 
     */
	Activity activity;
    
    // Does the user have the premium upgrade?
    public boolean mIsPremium = false;

    /** 
     * SKUs for our products as it appears on google play
     */
    static final String SKU_PREDICTION = "prediction2";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    
    // The helper object
    IabHelper mHelper;
    
    String payload;
    
    PurchaseHolder(Activity activity){
    	this.activity = activity;
    	setupAppBilling();
    }
    
	private void setupAppBilling() {
    	 payload = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
//         FacebookSdk.sdkInitialize(getApplicationContext());

         String base64EncodedPublicKey = activity.getResources().getString(R.string.api_google_key);

         // Create the helper, passing it our context and the public key to verify signatures with
         mHelper = new IabHelper(activity, base64EncodedPublicKey);

         // enable debug logging (for a production application, you should set this to false).
         mHelper.enableDebugLogging(true);

         // Start setup. This is asynchronous and the specified listener
         // will be called once setup completes.
         mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
             public void onIabSetupFinished(IabResult result) {

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
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREDICTION);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
        
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

            if (purchase.getSku().equals(SKU_PREDICTION)) {
                mIsPremium = true;
                //setWaitScreen(false);
            }

        }
    };

    // User clicked the "Upgrade to Premium" button.
    public void onBillingAcceptedButtonClicked() {
        mHelper.launchPurchaseFlow(activity, SKU_PREDICTION, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }
    
    
    
    /*
     * 
     * END OF BILLING
     * 
     */

}
