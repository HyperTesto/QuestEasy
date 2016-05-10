package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

		FSDeclarationDao fsd = new FSDeclarationDao(this.getApplicationContext());

		fsd.clear();
		fsd.populate();

		fsd.open();

		HashMap<Date, Declaration> decs = fsd.getAllDeclarations();

		for (Date k : decs.keySet()){
			items.add(decs.get(k));
		}

		fsd.close();

		ListView lv = (ListView) findViewById(R.id.lvDichiarazioni);
		DeclarationListAdapter adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);

		//ModelTestMethods.testWriteReadSomeCrap(this.getApplicationContext());

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object o = parent.getItemAtPosition(position);

				if (o instanceof Declaration) {
					Declaration target = (Declaration) o;
					System.out.println(target.getDate());

					Intent intent = new Intent(HomeActivity.this, EditDecActivity.class);
					intent.putExtra("DEC", target);

					startActivity(intent);
				}
			}
		});

		insertNewDcard = (FloatingActionButton)findViewById(R.id.fab);
		insertNewDcard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, EditDecActivity.class));
			}
		});

	}
}
