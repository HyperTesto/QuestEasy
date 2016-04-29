package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.DecListAdapter;
import me.hypertesto.questeasy.model.listitems.DecListItem;

public class HomeActivity extends AppCompatActivity {

	private FloatingActionButton insertNewDcard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ArrayList<DecListItem> items = new ArrayList<>();
		items.add(new DecListItem(true, "20/14/18 - 23/28/12", "caccacaccacaccacaccaccaca"));
		items.add(new DecListItem(false, "20/5439/18 - 122/28/12", "hkfsadfhafhaskfhaksdjf"));
		items.add(new DecListItem(true, "20/14/18 - 23/28/12", "caccacaccacaccacaccaccaca"));
		items.add(new DecListItem(false, "20/5439/18 - 122/28/12", "hkfsadfhafhaskfhaksdjf"));
		items.add(new DecListItem(true, "20/14/18 - 23/28/12", "caccacaccacaccacaccaccaca"));
		items.add(new DecListItem(false, "20/5439/18 - 122/28/12", "hkfsadfhafhaskfhaksdjf"));
		items.add(new DecListItem(true, "20/14/18 - 23/28/12", "caccacaccacaccacaccaccaca"));
		items.add(new DecListItem(false, "20/5439/18 - 122/28/12", "hkfsadfhafhaskfhaksdjf"));
		items.add(new DecListItem(true, "20/14/18 - 23/28/12", "caccacaccacaccacaccaccaca"));
		items.add(new DecListItem(false, "20/5439/18 - 122/28/12", "hkfsadfhafhaskfhaksdjf"));

		ListView lv = (ListView) findViewById(R.id.lvDichiarazioni);

		DecListAdapter adapter = new DecListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);

		insertNewDcard = (FloatingActionButton)findViewById(R.id.fab);
		insertNewDcard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this,EditDecActivity.class));
			}
		});

	}
}
