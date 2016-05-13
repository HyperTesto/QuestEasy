package me.hypertesto.questeasy.utils;


import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;

import me.hypertesto.questeasy.R;

/**
 * Created by hypertesto on 13/05/16.
 */
public class FabToggleAnimation extends Handler {

	public FabToggleAnimation (final View o){
		postDelayed(new Runnable() {
			@Override
			public void run() {
				//insertNewDcard.show(true);
				//o.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_from_bottom));
				//o.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_to_bottom));
			}
		}, 300);
	}

}

