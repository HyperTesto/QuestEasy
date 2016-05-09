package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.hypertesto.questeasy.R;

/**
 * Created by hypertesto on 09/05/16.
 */
public class PlaceAutocompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    //private List<Book> resultList = new ArrayList<Book>();
    private List<String> resultList = new ArrayList<>();

    public PlaceAutocompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.autocompleted_text)).setText(getItem(position));
        //((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getAuthor());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Filter.FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<String> books = findPlace(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<String> findPlace(Context context, String bookTitle) {
        // GoogleBooksProtocol is a wrapper for the Google Books API
        //GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
        //return protocol.findBooks(bookTitle);
        return "test";
    }
}

