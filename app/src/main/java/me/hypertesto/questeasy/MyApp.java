package me.hypertesto.questeasy;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by hypertesto on 31/05/16.
 */
@ReportsCrashes(
		formUri = "https://questura.hypertesto.me/api/v1/reportchrash"
)
public class MyApp extends Application {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		// The following line triggers the initialization of ACRA
		ACRA.init(this);
	}

}
