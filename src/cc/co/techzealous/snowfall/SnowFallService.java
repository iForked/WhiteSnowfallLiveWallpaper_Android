package cc.co.techzealous.snowfall;

import java.util.ArrayList;
import java.util.Random;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import cc.co.techzealous.snowfall.utils.SnowFallConstants;
import cc.co.techzealous.snowfall.utils.Snowflake;

public class SnowFallService extends WallpaperService {

	
	
	@Override
	public Engine onCreateEngine() {
		return new SnowFallEngine();
	}
	
	private class SnowFallEngine extends Engine {
		
		private Canvas mainCanvas;
		private SurfaceHolder mySurfaceHolder;
		private Paint snowPaint;
		private boolean isSurfaceVisible;
		private int surfaceWidth;
		private int surfaceHeight;
	
		private Handler myHandler;
		private Runnable runnableMain;
		private Random myRandom;
		private ArrayList<Snowflake> mySnowflakes;
		private Snowflake snowflake;
	
		private SharedPreferences prefs;
	
		private Matrix myMatrix;
		private Bitmap myImage;
		private boolean isImageLoaded;
		
		/* initialize when the wallpaper is created */
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			prefs = PreferenceManager.getDefaultSharedPreferences(SnowFallService.this);
			runnableMain = new Runnable() {
				public void run() {
					makeSnowflakes();
				}
			};
			myHandler = new Handler();
			myRandom = new Random();
			mySnowflakes = new ArrayList<Snowflake>();
			snowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			snowPaint.setColor(Color.WHITE);
			myMatrix = new Matrix();
			
			try {
				mainCanvas = surfaceHolder.lockCanvas();
				if(mainCanvas != null) {
					surfaceWidth = mainCanvas.getWidth();
					surfaceHeight = mainCanvas.getHeight();
				} else {
					surfaceWidth = 320;
					surfaceHeight = 480;
				}
			} finally {
				if(mainCanvas != null) {
					surfaceHolder.unlockCanvasAndPost(mainCanvas);
				}
			}
			
			
		}
		
		/* Start drawing on the canvas when the wallpaper is visible and stop when is not visible */
		@Override
		public void onVisibilityChanged(boolean visible) {
			isSurfaceVisible = visible;
			if(isSurfaceVisible) {
				myHandler.post(runnableMain);
			} else {
				myHandler.removeCallbacks(runnableMain);
			}
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
			super.onSurfaceCreated(surfaceHolder);
		}
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
			super.onSurfaceDestroyed(surfaceHolder);
			isSurfaceVisible = false;
			myHandler.removeCallbacks(runnableMain);
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
			super.onSurfaceChanged(surfaceHolder, format, width, height);
			surfaceWidth = width;
			surfaceHeight = height;
			myHandler.post(runnableMain);
		}
		
		private void makeSnowflakes() {
			mySurfaceHolder = getSurfaceHolder();
			mainCanvas = null;
			//lock the canvas, make snowflakes objects, draw them in the drawSnowflakes method
			try {
				mainCanvas = mySurfaceHolder.lockCanvas();
				if(mainCanvas != null) {
					if(mySnowflakes.size() == 0) {
						snowflake = new Snowflake();
						initSnowflake(snowflake);
						mySnowflakes.add(snowflake);
					}
					int snowflakesCount = prefs.getInt(SnowFallConstants.PREF_SNOW_AMOUNT, SnowFallConstants.PREF_DEFAULT_AMOUNT);
					if(mySnowflakes.size() > snowflakesCount) {
						mySnowflakes.remove(mySnowflakes.size() - 1);
					} else if(mySnowflakes.size() < snowflakesCount) {
						snowflake = new Snowflake();
						initSnowflake(snowflake);
						mySnowflakes.add(snowflake);
					}
					drawSnowflakes(mainCanvas);
				}
			} finally {
				if(mainCanvas != null) {
					mySurfaceHolder.unlockCanvasAndPost(mainCanvas);
				}
			}
			//removeCallbacks to the runnable, check if the LiveWallpaper is visible and post to redraw again after the set delay
			myHandler.removeCallbacks(runnableMain);
			if(isSurfaceVisible) {
				myHandler.postDelayed(runnableMain, prefs.getInt(SnowFallConstants.PREF_FPS, SnowFallConstants.PREF_DEFAULT_FPS));
			}
		}
		
		private void drawSnowflakes(Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			
			//if a new image was set load it
			if(prefs.getBoolean(SnowFallConstants.PREF_RELOAD_BACKGROUN, false)) {
				myImage = null;
				myImage = BitmapFactory.decodeFile(prefs.getString(SnowFallConstants.PREF_BACKGROUND_PATH, ""));
				prefs.edit().putBoolean(SnowFallConstants.PREF_RELOAD_BACKGROUN, false).commit();
			}
			
			//load the image only once
			if(prefs.contains(SnowFallConstants.PREF_BACKGROUND_PATH) && !isImageLoaded) {
				myImage = null;
				myImage = BitmapFactory.decodeFile(prefs.getString(SnowFallConstants.PREF_BACKGROUND_PATH, ""));
				
				isImageLoaded = true;
			}
			if(!prefs.contains(SnowFallConstants.PREF_BACKGROUND_PATH)) {
				isImageLoaded = false;
			}
			//draw the image on the canvas if an image was already loaded
			if(isImageLoaded) {
				canvas.drawBitmap(myImage, myMatrix, null);
			} else {
				myImage = null;
			}
			
			//draw the snow flakes on the canvas (over the image)
			for(Snowflake flake : mySnowflakes) {
				snowPaint.setAlpha(flake.getTransparency());
				canvas.drawCircle(flake.getPosX(), flake.getPosY(), flake.getSize(), snowPaint);
				
				if(flake.getPosY() > surfaceHeight) {
					initSnowflake(flake);
				} else {
					if(myRandom.nextBoolean()) {
						flake.setPosX(flake.getPosX() + myRandom.nextInt(flake.getSway()));
					} else {
						flake.setPosX(flake.getPosX() - myRandom.nextInt(flake.getSway()));
					}
					
					flake.setPosY(flake.getPosY() + flake.getSpeed());
				}
			}
		}
		//make changes to the snow flake attributes
		private void initSnowflake(Snowflake aFlake) {
			aFlake.setPosX(myRandom.nextInt(surfaceWidth) + 1);
			aFlake.setPosY(myRandom.nextInt(surfaceHeight / 3));
			aFlake.setSpeed(myRandom.nextInt(prefs.getInt(SnowFallConstants.PREF_SNOW_SPEED, SnowFallConstants.PREF_DEFAULT_SPEED)) + 1);
			aFlake.setSize(myRandom.nextInt(prefs.getInt(SnowFallConstants.PREF_SNOW_SIZE, SnowFallConstants.PREF_DEFAULT_SIZE)) + 1);
			aFlake.setTransparency(myRandom.nextInt(254) + 1);
			aFlake.setSway(myRandom.nextInt(SnowFallConstants.PREF_SWAY) + 1);
		}
	}

}
