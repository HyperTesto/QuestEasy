package me.hypertesto.questeasy.model.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by gianluke on 25/05/16.
 */
public class SwitchImageAdapter extends PagerAdapter{

	private ArrayList<String>pathsPhoto;
	private Context context;

	public SwitchImageAdapter (ArrayList<String> pathsPhoto){
		this.pathsPhoto = pathsPhoto;
	}

	@Override
	public int getCount(){
		return this.pathsPhoto.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, int position){
		ImageView img = new ImageView(container.getContext());
		img.setImageDrawable(getImageFromSdCard(pathsPhoto.get(position)));
		container.addView(img, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		return img;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		container.removeView((View)object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object){
		return view == object;
	}

	public Drawable getImageFromSdCard(String pathImage) {
		Drawable d = null;
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(pathImage);
			d = new BitmapDrawable(bitmap);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		return d;

	}

}
