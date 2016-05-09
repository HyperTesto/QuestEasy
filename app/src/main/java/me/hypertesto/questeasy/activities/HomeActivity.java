package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.adapters.DeclarationListAdapter;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.model.testcrap.ModelTestMethods;

public class HomeActivity extends AppCompatActivity {

	private FloatingActionButton insertNewDcard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ArrayList<Declaration> items = new ArrayList<>();
		items.add(new Declaration(new Date(), true));
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), true));
		items.add(new Declaration(new Date(), true));
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), true));

		ListView lv = (ListView) findViewById(R.id.lvDichiarazioni);
		DeclarationListAdapter adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);

		ModelTestMethods.testWriteReadSomeCrap(this.getApplicationContext());

		insertNewDcard = (FloatingActionButton)findViewById(R.id.fab);
		insertNewDcard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, EditDecActivity.class));
			}
		});

	}
}
