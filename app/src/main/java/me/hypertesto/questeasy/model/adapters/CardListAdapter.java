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
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.SingleGuestCard;

/**
 * Created by rigel on 03/05/16.
 */
public class CardListAdapter extends ArrayAdapter<Card> {
	private int layoutId;
	private Context context;
	private ArrayList<Card> items;

	public CardListAdapter(Context context, int layoutId, ArrayList<Card> items) {
		super(context, layoutId, items);
		this.context = context;
		this.layoutId = layoutId;
		this.items = items;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.layoutId, null);

		Card item = this.items.get(position);

		ImageView img = (ImageView) view.findViewById(R.id.cardWarningImg);
		if (item.isComplete()){
			//Remove warning sign
			img.setVisibility(View.GONE);
		}

		TextView txtName = (TextView) view.findViewById(R.id.cardName);
		txtName.setText(item.getTitle());

		TextView txtDesc = (TextView) view.findViewById(R.id.cardDesc);
		txtDesc.setText(item.getDescription());

		ImageView typeImg = (ImageView) view.findViewById(R.id.cardTypeImg);

		if (item instanceof SingleGuestCard){
			typeImg.setImageResource(R.drawable.guest_single3);
		} else if (item instanceof FamilyCard){
			typeImg.setImageResource(R.drawable.guest_family3);
		} else if (item instanceof GroupCard){
			typeImg.setImageResource(R.drawable.guest_group3);
		} else {
			throw new RuntimeException("Unknown card type");
		}

		return view;
	}
}
