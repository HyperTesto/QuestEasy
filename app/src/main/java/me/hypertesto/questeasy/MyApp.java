package me.hypertesto.questeasy;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import static org.acra.ReportField.*;

/**
 * Created by hypertesto on 31/05/16.
 */
@ReportsCrashes(
		formUri = "http://questura.hypertesto.me:8080/api/v1/crashreport",
		customReportContent = { REPORT_ID, STACK_TRACE, USER_CRASH_DATE }
)
public class MyApp extends Application {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		// The following line triggers the initialization of ACRA
		ACRA.init(this);
	}

}
