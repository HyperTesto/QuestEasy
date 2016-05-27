package me.hypertesto.questeasy.activities;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.io.File;
import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.ui.ImagePagerFragment;

/*

This activity manages fragment's replacement

 */

public class ActivityGalleryV2 extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Fragment fr = null;
		String tag = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
		}


		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}



}
