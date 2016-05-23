package upmc.cigcount;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import upmc.cigcount.model.Pack;

/**
 * Created by Clément on 21/05/2016.
 */
public class PacksAdapter extends ArrayAdapter<Pack> {

    public PacksAdapter(Context context, ArrayList<Pack> packs) {
        super(context, 0, packs);
    }
    private SparseBooleanArray selectedPositions;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pack pack = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pack, parent, false);
        }
        // Lookup view for data population
        TextView packBrand = (TextView) convertView.findViewById(R.id.packBrand);
        // Populate the data into the template view using the data object
        packBrand.setText(pack.brand());

        // Return the completed view to render on screen
        return convertView;
    }

    public void removeSelection() {
        selectedPositions = new SparseBooleanArray();
        notifyDataSetChanged();
    }
}
