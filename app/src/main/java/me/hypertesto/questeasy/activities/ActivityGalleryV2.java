package me.hypertesto.questeasy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.ui.ImagePagerFragment;

/*

This activity manages fragment's replacement

 */

public class ActivityGalleryV2 extends AppCompatActivity {

	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		Fragment fr = null;
		String tag = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
		}


		getSupportFragmentManager().beginTransaction().add(R.id.gallery_container, fr).commit();
		fab = (FloatingActionButton) findViewById(R.id.fab_gallery);

	}



}
