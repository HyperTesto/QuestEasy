package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.adapters.DeclarationListAdapter;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;

public class HomeActivity extends AppCompatActivity {

	private ListView lv;
	private FloatingActionButton insertNewDcard;
	private DeclarationListAdapter adapter;
	private int mPreviousVisibleItem;


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

		lv = (ListView) findViewById(R.id.lvDichiarazioni);
		adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);

		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				final int checkedCount = lv.getCheckedItemCount();
				mode.setTitle(checkedCount + " Selected");
				SparseBooleanArray selected = adapter.getSelectedIds();
				adapter.toggleSelection(position);

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.delete_item_ba2v2, menu);
				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
					case R.id.delete:
						SparseBooleanArray selected = adapter.getSelectedIds();
						Log.e("selected", String.valueOf(selected));
						for (int i = (selected.size() - 1); i >= 0; i--) {
							if (selected.valueAt(i)) {
								Declaration selectedItem = adapter.getItem(selected.keyAt(i));
								adapter.remove(selectedItem);
							}
						}
						mode.finish();
						return true;
					default:
						return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				adapter.removeSelection();
			}
		});

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

		lv.setTextFilterEnabled(true);

		insertNewDcard = (FloatingActionButton)findViewById(R.id.fab);
		insertNewDcard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, EditDecActivity.class));
			}
		});
		insertNewDcard.hide(false);
		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				insertNewDcard.show(true);
				insertNewDcard.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_from_bottom));
				insertNewDcard.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_to_bottom));
			}
		}, 300);*/
		new FabAnimation(insertNewDcard, getApplicationContext());

		lv.setOnScrollListener(new ListScrollListener(insertNewDcard));

	}


}
