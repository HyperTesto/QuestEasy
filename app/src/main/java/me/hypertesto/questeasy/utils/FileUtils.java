package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.hypertesto.questeasy.activities.FormGuestActivity;

/**
 * Created by hypertesto on 23/05/16.
 * An extra class with some useful method to handle file storage
 */
public class FileUtils {

	// directory name to store captured images
	public static final String IMAGE_DIRECTORY_NAME = "QuestEasy";

	private static String QUESTURA_SUB_PATH = "/questura";

	/**
	 * Check if external storage is writable
	 * @return
	 */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Check if external storage is readable
	 * @return
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Get directory for questura on external storage formatted file, this file it's publicly
	 * accessible and persist after app unistall.
	 * @param fileName
	 * @return
	 */
	public static File getFileQuesturaStorageDir(String fileName) throws IOException {

		File file = null, dir = null, sdCard = null;
		sdCard = Environment.getExternalStorageDirectory();
		dir = new File(sdCard.getAbsolutePath() + QUESTURA_SUB_PATH);

		boolean mkdirs = dir.mkdirs();

		Log.d(StaticGlobals.logTags.FILE_DEBUG, "R: " + isExternalStorageReadable());
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "W: " + isExternalStorageWritable());
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "mkdirs(): " + mkdirs);	//This way to prevent bug if debug not activated
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "isDir(): " + dir.isDirectory());
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "sdCard: " + sdCard.getAbsolutePath());
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "sdCard: " + sdCard.isDirectory());

		/*if (!dir.mkdirs() || !dir.isDirectory()) {
			Log.e("[FILE_DBG]", "Directory not created");
		}*/

		file = new File (dir, fileName);
		Log.d(StaticGlobals.logTags.FILE_DEBUG, "file: " + file.getAbsolutePath());
		return file;
	}

	/**
	 * Get a file in app private directory (files here get deleted after app unistall)
	 * We should use this method to save everything except generated compiled files.
	 * @param ctx
	 * @param fileName
	 * @return
	 */
	public static File getAppFilesStorageDir (Context ctx, String fileName) {
		File f = new File(ctx.getFilesDir(), fileName);
		if (!f.getParentFile().mkdirs()){
			Log.d(StaticGlobals.logTags.FILE_DEBUG, "Directory not created");
		}
		return new File(ctx.getFilesDir(), fileName);
	}


	/**
	 * Creating file uri to store image
	 * @param type
	 */

	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * Convert teh given image's path to a Uri String
	 * @param pathImage
	 */

	public static String getImagePathUriToString(String pathImage) {
		return Uri.fromFile(new File(pathImage)).toString();
	}

	/**
	 * Returning image
	 * @param type
	 */


	public static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		System.out.println("LOLOLOLOLOLOLOL");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		System.out.println("timeStamp: " + timeStamp);
		File mediaFile;
		if (type == StaticGlobals.image.MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}

	/**Method to get all images' path
	 *
	 *@param pathsPhoto
	 */
	public static ArrayList<String> getFilePaths (ArrayList<String> pathsPhoto){
		File directory = new File(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+
				File.separator+ FileUtils.IMAGE_DIRECTORY_NAME);

		//check for directory
		if (directory.isDirectory()){

			//getting the list of file paths
			File [] listFiles = directory.listFiles();


			//Check for count
			if (listFiles.length > 0){
				for (int i = 0; i < listFiles.length; i++){

					String filePath = listFiles[i].getAbsolutePath();

					pathsPhoto.add(filePath);
				}

			}
			else{

			}
		}
		return pathsPhoto;
	}

}
