package me.hypertesto.questeasy.notifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.activities.HomeActivity;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.utils.StaticGlobals;

/**
 * Created by rigel on 26/05/16.
 */
public class NotificationIntentService extends IntentService {

	private static final int NOTIFICATION_ID = 1;
	private static final String ACTION_START = "ACTION_START";
	private static final String ACTION_DELETE = "ACTION_DELETE";
	private SharedPreferences sharedPref;

	public NotificationIntentService() {
		super(NotificationIntentService.class.getSimpleName());
	}

	public static Intent createIntentStartNotificationService(Context context) {
		Intent intent = new Intent(context, NotificationIntentService.class);
		intent.setAction(ACTION_START);
		return intent;
	}

	public static Intent createIntentDeleteNotification(Context context) {
		Intent intent = new Intent(context, NotificationIntentService.class);
		intent.setAction(ACTION_DELETE);
		return intent;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");

		try {
			String action = intent.getAction();
			if (ACTION_START.equals(action)) {
				processStartNotification();
			}
			if (ACTION_DELETE.equals(action)) {
				processDeleteNotification(intent);
			}
		} finally {
			WakefulBroadcastReceiver.completeWakefulIntent(intent);
		}
	}

	private void processDeleteNotification(Intent intent) {
		// Log something?
	}

	private void processStartNotification() {
		// Do something. For example, fetch fresh data from backend to create a rich notification?

		FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());

		fsd.open();

		boolean existsNotComplete = false;
		boolean existsNotSent = false;

		ArrayList<Declaration> declarations = fsd.getAllDeclarations();

		for (Declaration d : declarations){
			if (!d.isComplete()){
				existsNotComplete = true;
			}

			if (!d.isSent()){
				existsNotSent = true;
			}
		}

		fsd.close();

		String title = "QuestEasy Reminder";
		String message;

		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		boolean disable = sharedPref.getBoolean("pref_notify", false);
		Log.d(StaticGlobals.logTags.DEBUG, "Disable is " + disable);

		if (existsNotComplete){
			message = "Ci sono dichiarazioni incomplete";
		} else if (existsNotSent){
			message = "Ci sono dichiarazioni pronte per l'invio";
		} else {
			message = "Tutto ok :)";
			disable = true;
		}

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle(title)
				.setAutoCancel(true)
				.setColor(getResources().getColor(R.color.colorAccent))
				.setContentText(message)
				.setSmallIcon(R.drawable.ic_reminder);

		PendingIntent pendingIntent = PendingIntent.getActivity(this,
				NOTIFICATION_ID,
				new Intent(this, HomeActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

		final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		if (!disable){
			manager.notify(NOTIFICATION_ID, builder.build());
		} else {
			Log.i(getClass().getSimpleName(),"Skipping notification");
		}
	}
}
