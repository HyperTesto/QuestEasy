package me.hypertesto.questeasy.model.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.utils.FileUtils;

/**
 * Created by gianluke on 27/05/16.
 */
public class ImageAdapter extends PagerAdapter {

	private ArrayList<String> filePaths = new ArrayList<String>();

	private LayoutInflater inflater;
	private ImageLoader imageLoader2;
	private DisplayImageOptions options;

	private ArrayList<String> pictureUriStrings;


	/*
		Costructor for ImageAdapter

	 */
	public ImageAdapter(Context context, ImageLoader imageLoader, ArrayList<String> uris){
		inflater = LayoutInflater.from(context);
		this.imageLoader2 = imageLoader;

		if (uris == null){
			this.pictureUriStrings = new ArrayList<>();
		} else {
			this.pictureUriStrings = uris;
		}

		//load images' paths
		this.filePaths = FileUtils.getFilePaths(this.filePaths);

		//Option image display
		this.options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_loading_failure)
				.showImageOnFail(R.drawable.ic_loading_failure)
				.resetViewBeforeLoading(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	//gettin images' number
	@Override
	public int getCount() {
		return this.pictureUriStrings.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {

		View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
		assert imageLayout != null;
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

		//convert image path to Uri
		String uriS;
		//uriS = FileUtils.getImagePathUriToString(filePaths.get(position));
		uriS = this.pictureUriStrings.get(position);

		//load image
		imageLoader2.displayImage(uriS,imageView,options,new SimpleImageLoadingListener(){
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
							case IO_ERROR:
								message = "Input/Output error";
								break;
							case DECODING_ERROR:
								message = "Image can't be decoded";
								break;
							case OUT_OF_MEMORY:
								message = "Out Of Memory error";
								break;
							case UNKNOWN:
								message = "Unknown error";
								break;
						}
						Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
					}

				}

		);

		view.addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}



}
