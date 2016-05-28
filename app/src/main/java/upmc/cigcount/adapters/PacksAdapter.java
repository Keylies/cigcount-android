package upmc.cigcount.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import upmc.cigcount.R;
import upmc.cigcount.model.Pack;

/**
 * Allows to populate packs list view with packs brand
 */
public class PacksAdapter extends ArrayAdapter<Pack> {

    public PacksAdapter(Context context, ArrayList<Pack> packs) {
        super(context, 0, packs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pack pack = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pack, parent, false);
        }

        TextView packBrand = (TextView) convertView.findViewById(R.id.packBrand);
        packBrand.setText(pack.brand());

        return convertView;
    }
}
