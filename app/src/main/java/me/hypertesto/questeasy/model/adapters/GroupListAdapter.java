package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Guest;

/**
 * Created by hypertesto on 17/05/16.
 */
public class GroupListAdapter extends ArrayAdapter<Guest>{

	private int resource;
	private Context context;
	private ArrayList<Guest> items;

	public GroupListAdapter(Context context, int resource, ArrayList<Guest> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.items = objects;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(this.resource, null);

		Guest item = this.items.get(position);

		ImageView img = (ImageView) view.findViewById(R.id.guestWarningImg);
		if (item.isComplete()){
			//Remove warning sign
			img.setVisibility(View.GONE);
		}

		TextView txtName = (TextView) view.findViewById(R.id.guestNameAndSurname);

		/*
		 * Better handling of name/surname empty
		 */
		String guestName, guestSurname;
		guestName = item.getName();
		guestSurname = item.getSurname();

		txtName.setText(String.format("%s %s", 	guestName.equals("") ? "Ospite" : guestName,
												guestSurname.equals("") ? "Sconosciuto" : guestSurname));

		TextView placeOfBirth = (TextView) view.findViewById(R.id.guestBirthPlace);
		placeOfBirth.setText(item.getPlaceOfBirth().getName()); //FIXME: use new Place class

		ImageView typeImg = (ImageView) view.findViewById(R.id.guestTypeImg);

		ColorGenerator generator = ColorGenerator.MATERIAL;
		//int color = generator.getRandomColor();
		int color = item.getColor();

		/*
		 * We ensure to set at least a dummy name and initial letter if name is unset
		 */
		String initialLetter = "";
		if (item.getSurname().length() != 0)
			initialLetter = item.getName().substring(0,1);
		if (initialLetter.equals("")) {
			initialLetter = "S";
		}

		TextDrawable drawable = TextDrawable.builder().buildRoundRect(initialLetter,
				color, 100); //FIXME: and on empty name?
		typeImg.setImageDrawable(drawable);


		return view;
	}


}
