package com.santeh.rjhonsl.samplemap.BroadcastReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMS_BroadcastReceiver extends BroadcastReceiver {
	// Get the object of SmsManager
	public static String contactNumber="", contactName="" , branchName="";
	String branchCode="";
	Context ctx;
	Activity activity;
	int counter = 0;
	private SharedPreferences settings;

	public void onReceive(Context context, Intent intent) {
		ctx = context;
		ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);

		final Bundle extras = intent.getExtras();

		try {
			
			if (extras != null) {

				Object[] pdusObj = (Object[]) extras.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObj.length];
				
				for (int i = 0; i < pdusObj.length; i++){
					messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
				}
				//gets all values from new messages and puts it to variables for storage
				String finalmessage, finalNumber, finalBody;
				long finalDate; int finalStatus;
				finalNumber = messages[0].getDisplayOriginatingAddress();
				finalBody =  messages[0].getMessageBody();
				finalDate = messages[0].getTimestampMillis();
				finalStatus = messages[0].getStatus();
				
//				check if message is multipart or not
				if (messages.length == 1) {
					finalmessage = messages[0].getDisplayMessageBody();
				}
				//appends if message is multipart
				else{
					StringBuilder bodyText = new StringBuilder();
					for (int a = 0; a < messages.length; a++) {
					      bodyText.append(messages[a].getDisplayMessageBody());
					    }
					finalmessage = String.valueOf(bodyText);
				}

				if (finalBody.equalsIgnoreCase("findme")){

					Intent intentToFire = new Intent(
							ReceiverPositioningAlarm.ACTION_REFRESH_SCHEDULE_ALARM);
					intentToFire.putExtra(ReceiverPositioningAlarm.COMMAND,
							ReceiverPositioningAlarm.SENDER_ACT_DOCUMENT);
					intentToFire.putExtra("num", finalNumber);
					context.sendBroadcast(intentToFire);
					OnNewLocationListener onNewLocationListener = new OnNewLocationListener() {

						@Override
						public void onNewLocationReceived(Location location) {
							// use your new location here then stop listening
							ReceiverPositioningAlarm.clearOnNewLocationListener(this);
						}
					};
					// start listening for new location
					ReceiverPositioningAlarm.setOnNewLocationListener(onNewLocationListener);
				}


			} // bundle is null
		} catch (Exception e) {
			// Log.d("SmsReceiver", "Exception smsReceiver: " +e);
		}//end of Try/Catch
		
	}   //end of onreceive


}
