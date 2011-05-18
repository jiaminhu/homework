package edu.neu.madcourse.nuillegalbronze;

import edu.neu.madcourse.nuillegalbronze.R;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Members extends Activity {
	
	   public static final String PHONE_ID = "";	
	   public static final String TAG="MembersActivity";
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.members);
	      try {
			String versionName = 
				  getPackageManager().getPackageInfo(
						  getPackageName(),
						  0).versionName;
		    Bundle extras = getIntent().getExtras();
		    if(extras != null) {
		        String phone_id = extras.getString(PHONE_ID);
		        if(phone_id != null) {
		             TextView t = (TextView)findViewById(R.id.members_content);
		            if(t != null) {
		                String text=(String) t.getText();
		                text+="\nVersion:"+versionName;
		                text+="\nDevice ID:"+phone_id;
		                t.setText(text);
		            }
		        }	      
		    }			
		} catch (NameNotFoundException e) {
			Log.e(TAG,e.getMessage());			
		}

	   }
}
