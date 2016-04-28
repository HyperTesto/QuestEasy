package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.listitems.DecListItem;

/**
 * Created by rigel on 28/04/16.
 */
public class DecListAdapter extends ArrayAdapter<DecListItem> {
	private int layoutId;
	private Context context;
	private ArrayList<DecListItem> items;

	public DecListAdapter(Context context, int layoutId, ArrayList<DecListItem> items) {
		super(context, layoutId, items);
		this.context = context;
		this.layoutId = layoutId;
		this.items = items;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.layoutId, null);

		DecListItem item = this.items.get(position);

		ImageView img = (ImageView) view.findViewById(R.id.decWarningImg);
		if (!item.isWarningSign()){
			img.setVisibility(View.GONE);
		}

		TextView txtDate = (TextView) view.findViewById(R.id.decDate);
		txtDate.setText(item.getDate());

		TextView txtDesc = (TextView) view.findViewById(R.id.decCount);
		txtDesc.setText(item.getDescription());

		return view;
	}

}
