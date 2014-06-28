package cc.co.techzealous.snowfall.utils;

public class SnowFallConstants extends Object {

	private SnowFallConstants() {
		
	}
	
	/** int preference indicating the number of snow flakes to draw on the screen */
	public static final String PREF_SNOW_AMOUNT = "amount";
	public static final int PREF_DEFAULT_AMOUNT = 50;
	
	/** int preference indicating the speed of the snow flakes */
	public static final String PREF_SNOW_SPEED = "speed";
	public static final int PREF_DEFAULT_SPEED = 10;
	
	/** int preference indicating the size of the snow flakes */
	public static final String PREF_SNOW_SIZE = "size";
	public static final int PREF_DEFAULT_SIZE = 2;
	
	/** int preference indicating how often should the handler post in ms*/
	public static final String PREF_FPS = "fps";
	/** default value for PREF_FPS, handler will post after 60ms */
	public static final int PREF_DEFAULT_FPS = 30;
	
	public static int PREF_SWAY = 4;
	public static final long MAX_FILE_SIZE = 1048576;
	
	/** String preference indicating the path to a selected image to be used as background*/
	public static final String PREF_BACKGROUND_PATH = "background";
	/** boolean preference indicating that a new background was set and the image should be reloaded in memory */
	public static final String PREF_RELOAD_BACKGROUN = "reload";
	/** boolean preference indicating if the eula was accepted */
	public static final String PREF_EULA = "eula";
}
