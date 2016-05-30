package me.hypertesto.questeasy.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import me.hypertesto.questeasy.R;

/**
 * Created by hypertesto on 30/05/16.
 */
public class SettingsActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}


}
