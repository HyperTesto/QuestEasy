package me.hypertesto.questeasy.showcase;

import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Just an helper class to build LayoutParams to correct handle button placing in showcase
 * Created by hypertesto on 01/06/16.
 */
public class ButtonLayoutParams {

	Resources res;

	public ButtonLayoutParams (Resources res){
		this.res = res;
	}

	/**
	 * LayoutParams to place button BOTTOM-LEFT
	 * @return
	 */
	public RelativeLayout.LayoutParams bottomLeft(){
		RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		int margin = ((Number) (res.getDisplayMetrics().density * 12)).intValue();
		lps.setMargins(margin, margin, margin, margin);
		return lps;
	}

	/**
	 * LayoutParams to place button CENTER
	 * @return
	 */
	public RelativeLayout.LayoutParams center(){
		RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lps.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lps.addRule(RelativeLayout.CENTER_VERTICAL);
		int margin = ((Number) (res.getDisplayMetrics().density * 12)).intValue();
		lps.setMargins(margin, margin, margin, margin);
		return lps;
	}
}
