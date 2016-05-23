package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by hypertesto on 23/05/16.
 * An extra class with some useful method to handle file storage
 */
public class FileUtils {

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
	public static File getFileQuesturaStorageDir(String fileName) {

		File file = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOCUMENTS), fileName);
		} else {
			file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
		}
		if (!file.mkdirs()) {
			Log.e("[FILE_DBG]", "Directory not created");
		}
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
		return new File(ctx.getFilesDir(), fileName);
	}

}
