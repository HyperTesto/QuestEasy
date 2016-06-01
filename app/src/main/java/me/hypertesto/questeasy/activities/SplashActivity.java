package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.hypertesto.questeasy.R;

public class SplashActivity extends AppCompatActivity {

	private static int SPLASH_TIMEOUT_MILLIS = 3000;
	Intent homeIntent;
	Handler handler;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		homeIntent = new Intent(SplashActivity.this, HomeActivity.class);

		handler = new Handler();

		runnable = new Runnable(){
			@Override
			public void run() {
				startActivity(homeIntent);
				finish();
			}
		};
	}

	@Override
	protected void onResume(){
		super.onResume();
		handler.postDelayed(runnable, SPLASH_TIMEOUT_MILLIS);
	}

	@Override
	protected void onPause(){
		super.onPause();
		handler.removeCallbacks(runnable);
	}
}
