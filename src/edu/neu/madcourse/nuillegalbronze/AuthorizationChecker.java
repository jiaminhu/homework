package edu.neu.madcourse.nuillegalbronze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class AuthorizationChecker {
	private static final String TAG = "AuthorizationChecker";
	
	private static final String[] someIDs = { 
		"000000000000000", // Emulator
		"357779031995200", // Tracy's HTC HD2 
		"A0000022B98A36", //Mobile Health Lab Droid X
		"A0000022BDBD34", //Mobile Health Lab Droid X
		"358512032560155", //Mobile Health Lab Aria
		"354957031620522", //Mobile Health Lab Nexus One
		"A1000017881446", //Mobile Health Lab Verizon Incredible
		"351801040062404", //Mobile Health Lab Galaxy S
		"351801040057388", //Mobile Health Lab Galaxy S 
		"A00000229967AC", //Mobile Health Lab Droid X
		"351801040057396" //Mobile Health Lab Samsung Galaxy S
		};	

	private static boolean IsAuthorized(Context aContext) {
		Log.v("myID", "myID1 is:");		
		String myID = GetID(aContext);
		Log.v("myID", "myID2 is:");
		Log.d(TAG, "myID is:"+myID);
		
		for (String anID : someIDs) {
			
		if (anID.equals(myID))
		return true;
		}
		return false; 
		}

	private static boolean IsAuthorized(Context aContext, boolean isShowToast) {
		try {
		if (IsAuthorized(aContext)) {
		if (isShowToast)
		Toast.makeText(aContext, "Authorized phone", Toast.LENGTH_SHORT).show();
		return true;
		} else {
		Toast.makeText(aContext, "This is a test application not intended for the public. Please uninstall.", Toast.LENGTH_LONG).show();
		return false;
		}
		} catch (Exception e) {
		Log.e("check", "Not authorized" );
		return false;
		}
		}	

	public static void DoAuthorization(Context aContext, Activity anActivity) {
		DoAuthorization(aContext, anActivity, false);
		}	
	
	/* Command to call in onCreate method that will check if the phone is 
	* authorized and, if not, start the uninstall program. 
	* @param aContext UI context
	* @param anActivity This is the activity that will be uninstalled
	* @param isShowToast True to show toasts of the status 
	*/
	public static void DoAuthorization(Context aContext, Activity anActivity, boolean isShowToast) {
	if (IsAuthorized(aContext, isShowToast)) {
	Log.v("check", "Authorized");
	return;
	}

	Log.v(TAG, "Not Authorized");
	String packageURI_string = "package:" + aContext.getPackageName().trim();
	Uri packageURI = Uri.parse(packageURI_string);
	Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
	uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	aContext.startActivity(uninstallIntent);
	anActivity.finish();
	}	
	
	
	/* Get the phone's ID number. This is unique to every phone. 
	* You must add the permission to access telephony in the Manifest:
	* android.permission.READ_PHONE_STATE 
	* @param aContext UI context
	* @return String of the ID. Returns empty string if unable to get the ID.
	*/
	public static String GetID(Context aContext) {
	TelephonyManager mTelephonyMgr = null;

	try {
	mTelephonyMgr = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);
	} catch (Exception e) {
	Log.e(TAG, "Could not get access to TelephonyManager. Most likely manifest does not include android.permission.READ_PHONE_STATE permission.");
	return "";
	}
Log.v("whatever","id is whatever");
	Log.d(TAG, "ID discovered is :" + mTelephonyMgr.getDeviceId());
	return mTelephonyMgr.getDeviceId();
	}

}
