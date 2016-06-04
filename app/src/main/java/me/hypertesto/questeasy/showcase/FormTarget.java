package me.hypertesto.questeasy.showcase;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.github.amlcurran.showcaseview.targets.Target;
import com.roughike.bottombar.BottomBar;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.ui.PermanenzaFragment;

/**
 * Simple class to handle targets in FormGuestActivity
 * Created by hypertesto on 02/06/16.
 */
public class FormTarget implements Target {

	Object o;
	int index;
	Context context;

	public FormTarget(Object o, Context context){
		this.o = o;
		index = 2;
		this.context = context;
	}

	@Override
	public Point getPoint() {

		if (o instanceof PermanenzaFragment){
			PermanenzaFragment f = (PermanenzaFragment)o;
			View v = f.getView().findViewById(R.id.input_permanenza);
			int[] coord = new int[2];
			v.getLocationInWindow(coord);
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			int width = size.x;
			int height = size.y;
			return new Point(coord[0] + v.getMeasuredWidth() /2,coord[1] + v.getMeasuredHeight() /2); //centre of the screen
		} else if (o instanceof BottomBar) {
			BottomBar b = (BottomBar)o;
			int[] coord = new int[2];
			b.getChildAt(index--).getLocationInWindow(coord); //in teoria dovrei prendermi i figli dall'ultimo al primo
			return new Point(coord[0],coord[1]);
		}

		return null;
	}
}
