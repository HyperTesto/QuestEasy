package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.SingleGuestCard;

/**
 * Created by rigel on 03/05/16.
 */
public class CardListAdapter extends ArrayAdapter<Card> implements Filterable {
	private int layoutId;
	private Context context;
	private ArrayList<Card> items;
	private ArrayList<Card> itemsSupport;
	private SparseBooleanArray mSelectedItems;

	//private ArrayList<Card> filterList;

	//private ValueFilter valueFilter;

	public CardListAdapter(Context context, int layoutId, ArrayList<Card> items) {
		super(context, layoutId, items);
		this.context = context;
		this.layoutId = layoutId;
		this.items = items;
		this.itemsSupport = new ArrayList<Card>();
		this.itemsSupport.addAll(items);
		this.mSelectedItems = new SparseBooleanArray();
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

		ColorGenerator generator = ColorGenerator.MATERIAL;
		int color = generator.getRandomColor();
		TextDrawable drawable = TextDrawable.builder().buildRoundRect(item.getInitialLetter(),
				color, 100);
		typeImg.setImageDrawable(drawable);
		 if(!mSelectedItems.valueAt(position)){
			 //insert here code
		 }



		return view;
	}


	public void filter (ArrayList filterParameters){
		items.clear();
		if (filterParameters.size() == 0){
			items.addAll(itemsSupport);
			System.out.println("SONO QUI ");
		}
		else{
			boolean singolo = filterParameters.contains("OSPITE SINGOLO");
			System.out.println("OSPITE SINGOLO "+singolo);
			boolean gruppo = filterParameters.contains("GRUPPO");
			System.out.println("GRUPPO "+gruppo);
			boolean famiglia = filterParameters.contains("FAMIGLIA");
			System.out.println("FAMIGLIA "+famiglia);
			for (Card c : itemsSupport){
				if ((c instanceof SingleGuestCard)&&(singolo)){
					items.add(c);
				}
				if ((c instanceof FamilyCard)&&(famiglia)){
					items.add(c);
				}
				if ((c instanceof GroupCard)&&(gruppo)){
					items.add(c);
				}
			}
		}
		notifyDataSetChanged();
	}


/*
	@Override
	public Filter getFilter() {
		if (valueFilter == null) {
			valueFilter = new ValueFilter();
		}
		return valueFilter;
	}

	private class ValueFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (constraint != null && constraint.length() > 0) {
				ArrayList<Card> filterList = new ArrayList<Card>();
				for (int i = 0; i < filterList.size(); i++) {
					if ( (filterList.get(i).getTitle().toUpperCase() )
							.contains(constraint.toString().toUpperCase())) {
						Card card;
						card.set(filterList.get(i).);
						Card country = new Card(filterList.get(i)
								.getName() ,  mStringFilterList.get(i)
								.getIso_code() ,  mStringFilterList.get(i)
								.getFlag());

						filterList.add(country);
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = mStringFilterList.size();
				results.values = mStringFilterList;
			}
			return results;

		}

		@Override
		protected void publishResults(CharSequence constraint,
									  FilterResults results) {
			countrylist = (ArrayList<Country>) results.values;
			notifyDataSetChanged();
		}
	}
	*/


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
