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

public class SnowFallSizeActivity extends Activity {

	private SharedPreferences prefs;
	private Button buttonOk;
	private Button buttonCancel;
	private SeekBar seekBarSize;
	private TextView textViewCurrentValue;
	private TextView textViewLabel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amount);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		buttonOk = (Button) findViewById(R.id.buttonOkAmount);
		buttonCancel = (Button) findViewById(R.id.buttonCancelAmount);
		seekBarSize = (SeekBar) findViewById(R.id.seekBarAmount);
		textViewCurrentValue = (TextView) findViewById(R.id.textViewCurrentValue);
		textViewLabel = (TextView) findViewById(R.id.textViewLabel);
		
		seekBarSize.setMax(3);
		seekBarSize.setProgress(prefs.getInt(SnowFallConstants.PREF_SNOW_SIZE, SnowFallConstants.PREF_DEFAULT_SIZE));
		textViewLabel.setText(getResources().getString(R.string.size_of_snow));
		textViewCurrentValue.setText(String.valueOf(prefs.getInt(SnowFallConstants.PREF_SNOW_SIZE, SnowFallConstants.PREF_DEFAULT_SIZE)));
		
		seekBarSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textViewCurrentValue.setText(String.valueOf(progress + 1));
			}
		});
		
		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prefs.edit().putInt(SnowFallConstants.PREF_SNOW_SIZE, seekBarSize.getProgress() + 1).commit();
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
