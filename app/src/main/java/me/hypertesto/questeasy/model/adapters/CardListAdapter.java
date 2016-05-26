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
import me.hypertesto.questeasy.utils.StaticGlobals;

/**
 * Created by rigel on 03/05/16.
 */
public class CardListAdapter extends ArrayAdapter<Card> implements Filterable {
	private int layoutId;
	private Context context;
	private ArrayList<Card> items;
	private ArrayList<Card> itemsFiltered;
	private SparseBooleanArray mSelectedItems;
	CardFilter mCardFilter;

	//private ArrayList<Card> filterList;

	//private ValueFilter valueFilter;

	public CardListAdapter(Context context, int layoutId, ArrayList<Card> items) {
		super(context, layoutId, items);
		this.context = context;
		this.layoutId = layoutId;
		this.items = new ArrayList<Card>();
		this.items.addAll(items);
		this.itemsFiltered = new ArrayList<Card>();
		this.itemsFiltered.addAll(items);
		this.mSelectedItems = new SparseBooleanArray();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.layoutId, null);

		Card item = this.itemsFiltered.get(position);

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
		//int color = generator.getRandomColor();
		int color = item.getMainGuest().getColor();

		/*
		 * We ensure to set at least a dummy name and initial letter if name is unset
		 */
		String initialLetter = "";
		if (item.getInitialLetter().length() != 0)
			initialLetter = item.getInitialLetter();
		if (initialLetter.equals("")) {
			initialLetter = "S";
		}


		TextDrawable drawable = TextDrawable.builder().buildRoundRect(initialLetter,
				color, 100);
		typeImg.setImageDrawable(drawable);
		 if(!mSelectedItems.valueAt(position)){
			 //insert here code
		 }

		return view;
	}


	private  class CardFilter extends Filter{
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();


			if (constraint == null || constraint.length()-2 == 0){

				results.values = items;
				results.count = items.size();

			}
			else{
				String costraintFilter = constraint.toString();
				boolean singolo = costraintFilter.contains(StaticGlobals.filterDialogOptions.FILTER_SINGLE);
				boolean gruppo = costraintFilter.contains(StaticGlobals.filterDialogOptions.FILTER_GROUP);
				boolean famiglia = costraintFilter.toString().contains(StaticGlobals.filterDialogOptions.FILTER_FAMILY);


				ArrayList<Card> filteredCard = new ArrayList<Card>();

				for (Card c : items){
					if (c instanceof SingleGuestCard && singolo) {
						filteredCard.add(c);
					}
					if (c instanceof FamilyCard && famiglia) {
						filteredCard.add(c);
					}
					if (c instanceof GroupCard && gruppo) {
						filteredCard.add(c);
					}
				}
				results.values = filteredCard;
				results.count = filteredCard.size();

			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
				itemsFiltered = (ArrayList<Card>) results.values;
				notifyDataSetChanged();
				clear();
				for (int i = 0, l = itemsFiltered.size(); i < l; i++)
					add(itemsFiltered.get(i));

		}
	}

	public Filter getFilter(){
		if (mCardFilter == null){
			mCardFilter = new CardFilter();
		}
		return mCardFilter;
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
