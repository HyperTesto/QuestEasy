package me.hypertesto.questeasy.utils;

import android.view.View;
import android.widget.AbsListView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Custom scroll listener that hide/show a View when the list is scrolled down / up
 * Created by hypertesto on 13/05/16.
 */
public class ListScrollListener implements AbsListView.OnScrollListener {

	private int mPreviousVisibleItem;
	private View obj;

	public ListScrollListener (View obj){
		this.obj = obj;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		if (obj instanceof FloatingActionMenu) {

			if (firstVisibleItem > mPreviousVisibleItem) {
				((FloatingActionMenu) obj).hideMenuButton(true);
			} else if (firstVisibleItem < mPreviousVisibleItem) {
				((FloatingActionMenu) obj).showMenuButton(true);
			} else {

				//
			}

		} else if (obj instanceof FloatingActionButton) {

			if (firstVisibleItem > mPreviousVisibleItem) {
				((FloatingActionButton) obj).hide(true);
			} else if (firstVisibleItem < mPreviousVisibleItem) {
				((FloatingActionButton) obj).show(true);
			} else {
				//System.out.println("Not implemented");
			}

		}

		mPreviousVisibleItem = firstVisibleItem;
	}

}
