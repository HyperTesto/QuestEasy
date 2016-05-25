package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

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
	public static File getFileQuesturaStorageDir(String fileName) throws IOException {

		File file = null, dir = null, sdCard = null;
		sdCard = Environment.getExternalStorageDirectory();
		dir = new File(sdCard.getAbsolutePath() + "/questura");

		Log.e("[FILE_DBG]", "R: " + isExternalStorageReadable());
		Log.e("[FILE_DBG]", "W: " + isExternalStorageWritable());
		Log.e("[FILE_DBG]", "mkdirs(): " + dir.mkdirs());
		Log.e("[FILE_DBG]", "isDir(): " + dir.isDirectory());
		Log.e("[FILE_DBG]", "sdCard: " + sdCard.getAbsolutePath());
		Log.e("[FILE_DBG]", "sdCard: " + sdCard.isDirectory());

		/*if (!dir.mkdirs() || !dir.isDirectory()) {
			Log.e("[FILE_DBG]", "Directory not created");
		}*/

		file = new File (dir, fileName);
		Log.e("[FILE_DBG]", "file: " + file.getAbsolutePath());
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
			Log.e("[FILE_DBG]", "Directory not created");
		}
		return new File(ctx.getFilesDir(), fileName);
	}

}
