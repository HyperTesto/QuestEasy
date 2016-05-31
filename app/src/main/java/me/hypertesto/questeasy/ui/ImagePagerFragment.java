/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package me.hypertesto.questeasy.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.ImageAdapter;
import me.hypertesto.questeasy.utils.StaticGlobals;


public class ImagePagerFragment extends Fragment {

	private ImageLoader imageLoader;
	private ArrayList<String> uriStrings;

	public ImagePagerFragment(){
		super();
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		ArrayList<String> arg = args.getStringArrayList(StaticGlobals.bundleArgs.STRING_URIS);
		if (arg != null){
			this.uriStrings = arg;
		} else {
			this.uriStrings = new ArrayList<>();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
		ViewPager pager = (ViewPager) rootView.findViewById(R.id.pagerV2);

		//Default settings for imageLoader
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration defaultconfiguration = new ImageLoaderConfiguration.
				Builder(getActivity().getApplicationContext()).
				threadPriority(Thread.NORM_PRIORITY -2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();

		//imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));
		imageLoader.init(defaultconfiguration);
		pager.setAdapter(new ImageAdapter(getActivity(), imageLoader, this.uriStrings));
		pager.setCurrentItem(0);
		return rootView;
	}

}