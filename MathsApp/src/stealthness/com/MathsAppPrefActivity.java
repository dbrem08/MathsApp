package stealthness.com;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

/**
 * this class provide a prefernce menu for MathsApp
 * @author Stealthness
 *
 */
public class MathsAppPrefActivity extends PreferenceActivity {
	
    private static final int defaultDecimalPlaces = 5;
	private SharedPreferences mPrefs;
    private int mCurViewMode;
    private int decimalPlaces;
	private boolean mShowScreenSplash;
	
	protected void onCreate(Bundle b){
		super.onCreate(b);
		addPreferencesFromResource(R.xml.mathsoptions);
		
        mPrefs = getSharedPreferences(null, 0);
        
        mCurViewMode = mPrefs.getInt("view_mode" ,0);
        decimalPlaces= mPrefs.getInt("decimal_places_pref",defaultDecimalPlaces);
        mShowScreenSplash = mPrefs.getBoolean("show_screen_splash", true);
	}

	
	public void onPause(){
		super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("view_mode", mCurViewMode);
        ed.putInt("decimal_places_pref",decimalPlaces);
        ed.putBoolean("show_screen_splash", mShowScreenSplash);
        ed.commit();
	}
}
