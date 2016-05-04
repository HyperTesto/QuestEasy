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
import me.hypertesto.questeasy.model.listitems.CardListItem;
import me.hypertesto.questeasy.model.listitems.DecListItem;

/**
 * Created by gianluke on 29/04/16.
 */
public class CardListAdapter extends ArrayAdapter<CardListItem> {
	private int layoutId;
	private Context context;
	private ArrayList<CardListItem> items;

	public CardListAdapter(Context context, int layoutId, ArrayList<CardListItem> items) {
		super(context, layoutId, items);
		this.context = context;
		this.layoutId = layoutId;
		this.items = items;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.layoutId, null);

		CardListItem item = this.items.get(position);

		ImageView img = (ImageView) view.findViewById(R.id.cardWarningImg);
		if (item.isComplete()) {
			img.setVisibility(View.GONE);
		}

		TextView txtName = (TextView) view.findViewById(R.id.cardName);
		txtName.setText(item.getName());

		TextView txtDesc = (TextView) view.findViewById(R.id.cardDesc);
		txtDesc.setText(item.getDescription());

		ImageView typeImg = (ImageView) view.findViewById(R.id.cardTypeImg);
		String type = item.getType();
		type = type.toUpperCase();
		switch (type) {
			case "SINGOLO":
				typeImg.setImageResource(R.drawable.guest_single3);
				break;
			case "GRUPPO":
				typeImg.setImageResource(R.drawable.guest_group3);
				break;
			case "FAMIGLIA":
				typeImg.setImageResource(R.drawable.guest_family3);
				break;
			default:
				throw new RuntimeException("Unknown card type");

		}

		return view;
	}
}
