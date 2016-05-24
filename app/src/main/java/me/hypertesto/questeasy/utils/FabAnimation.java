package me.hypertesto.questeasy.utils;


import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import me.hypertesto.questeasy.R;

/**
 * Perform custom hide/show animations on a View.
 * In this case we use it for fab animations.
 * Created by hypertesto on 13/05/16.
 */
public class FabAnimation extends Handler {

	public FabAnimation(final View o, final Context context){

		postDelayed(new Runnable() {
			@Override
			public void run() {
				if(o instanceof FloatingActionButton){

					FloatingActionButton fab = (FloatingActionButton) o;
					fab.show(true);
					fab.setShowAnimation(AnimationUtils.loadAnimation(context, R.anim.show_from_bottom));
					fab.setHideAnimation(AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom));

				} else if (o instanceof FloatingActionMenu){

					FloatingActionMenu fabMenu = (FloatingActionMenu) o;
					fabMenu.showMenuButton(true);
					fabMenu.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(context, R.anim.show_from_bottom));
					fabMenu.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom));

				} else {

					System.out.println("Not implemented yet");

				}

			}
		}, 200);
	}

}

