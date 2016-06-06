package me.hypertesto.questeasy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import me.hypertesto.questeasy.R;

/**
 * Vocal API guide
 * FIXME: i should make audio cards as a custom viewGroup, right now the resource file is rubbish
 * Created by hypertesto on 04/06/16.
 */
public class VocalGuideActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_vocal_api_guide);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TextView part_1 = (TextView) findViewById(R.id.api_guide_part_1);
		TextView part_2 = (TextView) findViewById(R.id.api_guide_part_2);
		TextView part_3 = (TextView) findViewById(R.id.api_guide_part_3);
		assert part_1 != null;
		part_1.setText(Html.fromHtml(getResources().getString(R.string.api_guide_intro)));
		assert part_2 != null;
		part_2.setText(Html.fromHtml(getResources().getString(R.string.api_guide_part_2)));
		assert part_3 != null;
		part_3.setText(Html.fromHtml(getResources().getString(R.string.api_guide_part_3)));

		//root.addView(audioBox);
	}
}
