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

}
