package cc.co.techzealous.snowfall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cc.co.techzealous.snowfall.utils.SnowFallConstants;

public class SnowFallEula extends Activity {

	private SharedPreferences prefs;
	private Button buttonOk;
	private Button buttonCancel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.eula);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		buttonOk = (Button) findViewById(R.id.buttonEulaOk);
		buttonCancel = (Button) findViewById(R.id.buttonEulaCancel);
		
		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prefs.edit().putBoolean(SnowFallConstants.PREF_EULA, true).commit();
				Intent i = new Intent(SnowFallEula.this, SnowFallPreferenceActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
