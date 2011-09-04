package mcg.android.Droid500px.helpers;

import android.content.SharedPreferences;

public class Settings {
	
	public static String AUTH_KEY = "AUTH_KEY";
	public static String AUTH_SECRET = "AUTH_SECRET";

	//these are set at Droid500pxActivity.initPreferences
	public static String CALLBACK_URL;
	static SharedPreferences prefs = null;
	
	public static void initSettings(SharedPreferences settings){
        prefs = settings;
	}
	
	public static void saveAccessCredentials(String token, String secret){
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(AUTH_KEY, token);
		editor.putString(AUTH_SECRET, secret);
		editor.commit();
	}

	public static String getAccessToken(){
		return prefs.getString(AUTH_KEY, null);
	}

	public static String getAccessSecret(){
		return prefs.getString(AUTH_SECRET, null);
	}
	
	public static boolean contains(String key){
		return prefs.contains(key);
	}
	
}
