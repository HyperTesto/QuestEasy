package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.adapters.DeclarationListAdapter;

public class HomeActivity extends AppCompatActivity {

	ListView lv;
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

		lv = (ListView) findViewById(R.id.lvDichiarazioni);

		final DeclarationListAdapter adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);

		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				final int checkedCount = lv.getCheckedItemCount();
				mode.setTitle(checkedCount + " Selected");
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
		insertNewDcard = (FloatingActionButton)findViewById(R.id.fab);
		insertNewDcard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, EditDecActivity.class));
			}
		});

	}
}
