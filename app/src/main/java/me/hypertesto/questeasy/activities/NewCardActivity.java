package me.hypertesto.questeasy.activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.hypertesto.questeasy.R;

public class NewCardActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switch (getResources().getConfiguration().orientation) {
			case Configuration.ORIENTATION_PORTRAIT:
				setContentView(R.layout.activity_new_card_v);
				break;
			case Configuration.ORIENTATION_LANDSCAPE:
				setContentView(R.layout.activity_new_card_o);
				break;
		}
	}
}
