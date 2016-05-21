package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;

/**
 * Created by rigel on 03/05/16.
 */
public class DeclarationListAdapter extends ArrayAdapter<Declaration> {
	private int layoutId;
	private Context context;
	LayoutInflater inflater;
	private ArrayList<Declaration> items;
	private SparseBooleanArray mSelectedItems;

	public DeclarationListAdapter(Context context, int layoutId, ArrayList<Declaration> items) {
		super(context, layoutId, items);
		mSelectedItems = new SparseBooleanArray();
		this.context = context;
		this.layoutId = layoutId;
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.layoutId, null);

		Declaration item = this.items.get(position);

		ImageView img = (ImageView) view.findViewById(R.id.decWarningImg);
		if (item.isComplete()){
			//Remove warning sign
			img.setVisibility(View.GONE);
		}

		TextView txtDate = (TextView) view.findViewById(R.id.decDate);
		Date date = item.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		txtDate.setText(sdf.format(date));

		TextView txtDesc = (TextView) view.findViewById(R.id.decCount);
		String desc = item.size() + " arrivi";
		txtDesc.setText(desc);


		return view;
	}

	@Override
	public void remove(Declaration object){
		items.remove(object);
		notifyDataSetChanged();
	}

	public ArrayList<Declaration> getDeclarationList(){
		return items;
	}

	public void toggleSelection(int position){
		selectView(position, !mSelectedItems.get(position));
	}

	public void removeSelection(){
		mSelectedItems = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView (int position, boolean value){
		if (value){
			mSelectedItems.put(position, value);
		}
		else{
			mSelectedItems.delete(position);
		}
	}

	public int getSelectedCount(){
		return mSelectedItems.size();
	}

	public SparseBooleanArray getSelectedIds(){
		return mSelectedItems;
	}

}
