package me.hypertesto.questeasy.showcase;

import android.graphics.Point;
import android.view.View;

import com.github.amlcurran.showcaseview.targets.Target;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Implement target for FloatingActionButton & FloatingActionMenu
 * Created by hypertesto on 02/06/16.
 */
public class FabTarget implements Target{

	View fab;

	public FabTarget(View fab){
		this.fab = fab;
	}

	@Override
	public Point getPoint() {
		int[] coord = new int[2];
		fab.getLocationInWindow(coord);
		//The simple case
		if (fab instanceof FloatingActionButton){
			return new Point(coord[0] + (int) fab.getPivotX(),
					coord[1] + (int) fab.getPivotY());
		//The complex case
		} else if (fab instanceof FloatingActionMenu){
			FloatingActionMenu f = (FloatingActionMenu)fab;
			int mMaxButtonWidth = 0;
			for (int i = 0; i < f.getChildCount(); i++){
				mMaxButtonWidth = Math.max(mMaxButtonWidth, f.getChildAt(i).getMeasuredWidth());
			}
			return  new Point(fab.getRight()- mMaxButtonWidth /2  - fab.getPaddingRight(),
					fab.getBottom() + f.getChildAt(f.getChildCount()-1).getMeasuredHeight() );
		}

		return null;

	}
}
