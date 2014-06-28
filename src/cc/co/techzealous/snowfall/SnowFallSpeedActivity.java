package cc.co.techzealous.snowfall;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import cc.co.techzealous.snowfall.utils.SnowFallConstants;

public class SnowFallSpeedActivity extends Activity {

	private SharedPreferences prefs;
	private Button buttonOk;
	private Button buttonCancel;
	private SeekBar seekBarSpeed;
	private TextView textViewCurrentValue;
	private TextView textViewLabel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amount);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		buttonOk = (Button) findViewById(R.id.buttonOkAmount);
		buttonCancel = (Button) findViewById(R.id.buttonCancelAmount);
		seekBarSpeed = (SeekBar) findViewById(R.id.seekBarAmount);
		textViewCurrentValue = (TextView) findViewById(R.id.textViewCurrentValue);
		textViewLabel = (TextView) findViewById(R.id.textViewLabel);
		
		seekBarSpeed.setMax(37);
		seekBarSpeed.setProgress(prefs.getInt(SnowFallConstants.PREF_SNOW_SPEED, SnowFallConstants.PREF_DEFAULT_SPEED));
		textViewLabel.setText(getResources().getString(R.string.speed_of_snow));
		textViewCurrentValue.setText(String.valueOf(prefs.getInt(SnowFallConstants.PREF_SNOW_SPEED, SnowFallConstants.PREF_DEFAULT_SPEED)));
		
		seekBarSpeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textViewCurrentValue.setText(String.valueOf(progress + 3));
			}
		});
		
		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prefs.edit().putInt(SnowFallConstants.PREF_SNOW_SPEED, seekBarSpeed.getProgress() + 3).commit();
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
