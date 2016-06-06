package me.hypertesto.questeasy.showcase;

import android.graphics.Point;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Class that holds all the showcase targets
 * Created by hypertesto on 04/06/16.
 */
public class ShowcaseTarget {

	/**
	 * ShowcaseTarget for FloatingActionButton, coordinates may not work perfectly on older devices
	 * Coordinates are the centre of the FloatingActionButton
	 * FIXME: maually calculate to have an accurate center on all devices
	 */
	public static class Fab implements com.github.amlcurran.showcaseview.targets.Target{

		private FloatingActionButton fab;

		public Fab(FloatingActionButton fab) {
			this.fab = fab;
		}

		@Override
		public Point getPoint() {
			int[] coord = new int[2];
			fab.getLocationInWindow(coord);
			return new Point(coord[0] + (int) fab.getPivotX(),
					coord[1] + (int) fab.getPivotY());
		}
	}

	/**
	 * ShowcaseTarget for FloatingActionMenu
	 * Coordinates are the centre of the primary FloatingActionButton
	 * FIXME: better calculate centre
	 */
	public static class FabMenu implements com.github.amlcurran.showcaseview.targets.Target{

		private FloatingActionMenu fabMenu;

		public FabMenu(FloatingActionMenu fabMenu) {
			this.fabMenu = fabMenu;
		}

		@Override
		public Point getPoint() {
			int mMaxButtonWidth = 0;
			for (int i = 0; i < fabMenu.getChildCount(); i++){
				mMaxButtonWidth = Math.max(mMaxButtonWidth, fabMenu.getChildAt(i).getMeasuredWidth());
			}
			//Calculate centre is a bit complicated...
			return  new Point(fabMenu.getRight()- mMaxButtonWidth /2  - fabMenu.getPaddingRight(),
					fabMenu.getBottom() + fabMenu.getChildAt(fabMenu.getChildCount()-1).getMeasuredHeight() );
		}
	}
}
