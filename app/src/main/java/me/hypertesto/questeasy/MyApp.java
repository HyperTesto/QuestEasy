package me.hypertesto.questeasy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import me.hypertesto.questeasy.activities.ActivityGalleryV2;
import me.hypertesto.questeasy.activities.EditCardActivity;
import me.hypertesto.questeasy.activities.EditDecActivity;
import me.hypertesto.questeasy.activities.HomeActivity;

import static org.acra.ReportField.*;

/**
 * Created by hypertesto on 31/05/16.
 */
@ReportsCrashes(
		formUri = "http://questura.hypertesto.me:8080/api/v1/crashreport",
		customReportContent = { REPORT_ID, STACK_TRACE, USER_CRASH_DATE }
)
public class MyApp extends Application {

	public static final String TUTORIAL_ON_STARTUP = "pref_tutorial";

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(base);

		/*
		 * If show tutorial on each startup is true, we overwrite every step value to show them
		 * NOTE: the very first startup show tutorial anyway.
		 */
		if (sharedPref.getBoolean(TUTORIAL_ON_STARTUP, false)){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(HomeActivity.TUTORIAL_FIRST_SHOWN,true);
			editor.putBoolean(EditDecActivity.TUTORIAL_SECOND_SHOWN,true);
			editor.putBoolean(ActivityGalleryV2.TUTORIAL_FOURTH_SHOWN, true);
			editor.putBoolean(EditCardActivity.TUTORIAL_FIFTH_SHOWN, true);
			editor.apply();
		}

		// The following line triggers the initialization of ACRA
		ACRA.init(this);
	}

}
