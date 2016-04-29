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
import me.hypertesto.questeasy.model.listitems.GuestListItem;

/**
 * Created by gianluke on 29/04/16.
 */
public class GuestListAdapter extends ArrayAdapter<GuestListItem> {

    private int layoutId;
    private Context context;
    private ArrayList<GuestListItem> guests;

    public GuestListAdapter(Context context, int layoutId, ArrayList<GuestListItem> guests) {
        super(context, layoutId, guests);
        this.context = context;
        this.layoutId = layoutId;
        this.guests = guests;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(this.layoutId, null);

        GuestListItem item = this.guests.get(position);

        ImageView img = (ImageView) view.findViewById(R.id.decWarningImg);
       /* if (!item.isWarningSign()){
            img.setVisibility(View.GONE);
        }

        TextView txtDate = (TextView) view.findViewById(R.id.decDate);
        txtDate.setText(item.getDate());

        TextView txtDesc = (TextView) view.findViewById(R.id.decCount);
        txtDesc.setText(item.getDescription());
        */
        return view;
    }
}
