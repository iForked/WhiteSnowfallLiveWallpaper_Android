package cc.co.techzealous.snowfall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cc.co.techzealous.snowfall.utils.SnowFallConstants;

public class SnowFallBackImageActivity extends Activity {

	private SharedPreferences prefs;
	private Button buttonCancel;
	private Button buttonSelect;
	private Button buttonClear;
	private TextView textViewCurrentImage;
	private int imageRequestCode = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backimage);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		buttonCancel = (Button) findViewById(R.id.buttonImageCancel);
		buttonSelect = (Button) findViewById(R.id.buttonImageSelect);
		buttonClear = (Button) findViewById(R.id.buttonImageClear);
		textViewCurrentImage = (TextView) findViewById(R.id.textViewCurrentImage);
		
		textViewCurrentImage.setText(prefs.getString(SnowFallConstants.PREF_BACKGROUND_PATH, "No image is set"));
		
		
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		buttonSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, Uri.parse("image/*"));
				i.setType("image/*");
				startActivityForResult(i, imageRequestCode);
			}
		});
		
		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prefs.edit().remove(SnowFallConstants.PREF_BACKGROUND_PATH).commit();
				Toast.makeText(SnowFallBackImageActivity.this, getResources().getString(R.string.image_cleared), Toast.LENGTH_SHORT).show();
				textViewCurrentImage.setText(prefs.getString(SnowFallConstants.PREF_BACKGROUND_PATH, "No image is set"));
			}
		});
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == imageRequestCode) {
			if(resultCode == Activity.RESULT_OK) {
				Uri imageUri = intent.getData();
				
				//solution 1
//				InputStream imageStream = null;
//				try {
//					imageStream = getContentResolver().openInputStream(imageUri);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//				Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//to get the image (applicable to both solutions): Bitmap slectedImage_s2 = BitmapFactory.decodeFile(filePath);
//after this canvas.drawBitmap(selectedImage_s2, new Matrix(), null);
				
				//solution 2
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				String[] fileSizeColumn = {MediaStore.Images.Media.SIZE};
				//get the file path
				Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
				String filePath = "";
				if(cursor.moveToFirst()) {
					int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
					filePath = cursor.getString(columnIndex);
				}
				//get the size of the image in bytes (it is long)
				cursor = getContentResolver().query(imageUri, fileSizeColumn, null, null, null);
				long fileSize = 0;
				if(cursor.moveToFirst()) {
					int columnIndexSize = cursor.getColumnIndexOrThrow(fileSizeColumn[0]);
					fileSize = cursor.getLong(columnIndexSize);
					cursor.close();
				}
				//if the size is more than 1.5MB don't load it
				if(fileSize > SnowFallConstants.MAX_FILE_SIZE) {
					Toast.makeText(SnowFallBackImageActivity.this, getResources().getString(R.string.max_size_exceeded), Toast.LENGTH_LONG).show();
				} else {
					prefs.edit().putString(SnowFallConstants.PREF_BACKGROUND_PATH, filePath).commit();
					prefs.edit().putBoolean(SnowFallConstants.PREF_RELOAD_BACKGROUN, true).commit();
				}
				finish();
			}
		}
	}
}
