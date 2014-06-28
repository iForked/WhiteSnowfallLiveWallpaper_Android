package cc.co.techzealous.snowfall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import cc.co.techzealous.snowfall.utils.SnowFallConstants;

public class SnowFallPreferenceActivity extends PreferenceActivity {

	private SharedPreferences prefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        addPreferencesFromResource(R.xml.snowfallpreferences);
        
        if(!prefs.getBoolean(SnowFallConstants.PREF_EULA, false)) {
        	Intent i = new Intent(this, SnowFallEula.class);
        	startActivity(i);
        	finish();
        }
    }
}
