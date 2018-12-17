package simplicity_an.simplicity_an;

/**
 * Created by kuppusamy on 3/19/2016.
 */


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;
//import android.support.multidex.MultiDex;


public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Context mAppContext;

   // @Override
   public void onCreate() {
     MultiDex.install(this);
        super.onCreate();
       OneSignal.startInit(this)
               .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
               .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
               .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
               .init();


        mInstance = this;

        this.setAppContext(getApplicationContext());
    }
protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      MultiDex.install(this);
    }
    public static MyApplication getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        String customKey=null;
        String type,video;
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;


            if (data != null) {
                customKey = data.optString("id", null);
                type=data.optString("qtype",null);
               video=data.optString("youtube",null);
                Log.e("NOTIFI","hi"+customKey+type);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);

            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            Log.e("Title:",customKey+"keys");

			/*if(type.equalsIgnoreCase("Sports")) {
				Intent intent = new Intent(getApplicationContext(), SportsnewsDescription.class);
				intent.putExtra("ID",customKey);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}else {

			}*/
            if(type!=null) {
                if (type.equals("news")) {
                    Intent intent = new Intent(getApplicationContext(), NewsDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("article")) {
                    Intent intent = new Intent(getApplicationContext(), Articledescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("doit")) {
                    Intent intent = new Intent(getApplicationContext(), DoitDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("farming")) {
                    Intent intent = new Intent(getApplicationContext(), Farmingdescription.class);
                    intent.putExtra("ID", customKey);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("food")) {
                    Intent intent = new Intent(getApplicationContext(), TipsDescription.class);
                    intent.putExtra("ID", customKey);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("govt")) {
                    Intent intent = new Intent(getApplicationContext(), GovernmentnotificationsDescriptions.class);
                    intent.putExtra("ID", customKey);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("health")) {
                    Intent intent = new Intent(getApplicationContext(), Healthylivingdescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("science")) {
                    Intent intent = new Intent(getApplicationContext(), ScienceandTechnologyDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("sports")) {
                    Intent intent = new Intent(getApplicationContext(), SportsnewsDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("travels")) {
                    Intent intent = new Intent(getApplicationContext(), TravelsDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equals("event")) {
                    Intent intent = new Intent(getApplicationContext(), EventsDescription.class);
                    intent.putExtra("ID", customKey);
                    /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                    startActivity(intent);
                } else if (type.equalsIgnoreCase("Radio")) {
                    //Intent intent = new Intent(getActivity(), Radioplayeractivity.class);
                    // intent.putExtra("ID", ids);
                    /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                    //startActivity(intent);
                } else if (type.equalsIgnoreCase("Music")) {
                    Intent intent = new Intent(getApplicationContext(), Musicplayerpage.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equalsIgnoreCase("Job")) {
                    Intent intent = new Intent(getApplicationContext(), JobsDetailPage.class);
                    intent.putExtra("ID", customKey);
                    //intent.putExtra("TITLE", itemmodel.getTitle());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (type.equalsIgnoreCase("theatre")) {
					Intent intent = new Intent(getApplicationContext(), YoutubeVideoPlayer.class);
					intent.putExtra("URL", video);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
                } else if (type.equalsIgnoreCase("education")) {
                    Intent intent = new Intent(getApplicationContext(), EducationDescription.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                    intent.putExtra("ID", customKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }else {

            }
            // The following can be used to open an Activity of your choice.

            // Intent intent = new Intent(getApplication(), YourActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);

            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //  if you are calling startActivity above.
         /*
            <application ...>
              <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
            </application>
         */
        }
    }
}