package upmc.cigcount.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.adapters.PacksAdapter;
import upmc.cigcount.R;
import upmc.cigcount.StatsActivity;
import upmc.cigcount.model.Cigarette;
import upmc.cigcount.model.Pack;
import upmc.cigcount.model.User;

/**
 * Dialog fragment which displays packs list
 * Allows to choose one pack to be the current pack
 */
public class StatsChangePackFragment extends DialogFragment {

    private ListView packsList;
    private PacksAdapter adapter;
    private User user;

    public StatsChangePackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_change_pack, null);
        packsList = (ListView) v.findViewById(R.id.packsList);
        user = CigCountApplication.getInstance().user();
        adapter = new PacksAdapter(getContext(), user.packs());

        builder.setView(v);
        builder.setTitle(R.string.change_pack_fragment_title);
        setList();

        builder.setNegativeButton(R.string.change_pack_fragment_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                StatsChangePackFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    /**
     * Check if there are packs and show the list
     * Clicked pack become the new current pack
     */
    private void setList() {
        packsList.setAdapter(adapter);
        registerForContextMenu(packsList);

        packsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pack selectedPack = user.packs().get(position);
                if(cigsExists(selectedPack)) {
                    ((StatsActivity) getActivity()).setCurrentPack(selectedPack);
                    StatsChangePackFragment.this.getDialog().cancel();
                } else
                    Toast.makeText(getContext(), R.string.no_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Check if cigarettes were added with this pack
     * @param selectedPack the selected Pack
     * @return true if cigarettes were added
     */
    private Boolean cigsExists(Pack selectedPack) {
        for(Cigarette c : user.cigSmoked())
            if(c.pack() == selectedPack)
                return true;
        return false;
    }

}
