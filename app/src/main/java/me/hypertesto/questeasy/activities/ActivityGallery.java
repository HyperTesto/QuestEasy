package me.hypertesto.questeasy.activities;

import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.SwitchImageAdapter;

public class ActivityGallery extends AppCompatActivity {

	ArrayList<String> filePaths = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_gallery);

		ViewPager viewPager =(ViewPager)findViewById(R.id.imageViewer);

		filePaths = getFilePaths(filePaths);


		SwitchImageAdapter switchImageAdapter = new SwitchImageAdapter(filePaths);
		viewPager.setAdapter(switchImageAdapter);



	}

	public static ArrayList<String> getFilePaths (ArrayList<String> pathsPhoto){
		File directory = new File(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+
				File.separator+FormGuestActivity.IMAGE_DIRECTORY_NAME);

		System.out.println("Directory "+directory.toString());
		System.out.println("List file length "+directory.listFiles().length);
		System.out.println("IS directory "+directory.isDirectory());
		//check for directory
		if (directory.isDirectory()){

			//getting the list of file paths
			File [] listFiles = directory.listFiles();

			System.out.println("List file length "+listFiles.length);
			//Check for count
			if (listFiles.length > 0){
				for (int i = 0; i < listFiles.length; i++){

					String filePath = listFiles[i].getAbsolutePath();
					System.out.println("File path "+filePath);

					pathsPhoto.add(filePath);
				}

			}
			else{

			}
		}
		return pathsPhoto;
	}



}
