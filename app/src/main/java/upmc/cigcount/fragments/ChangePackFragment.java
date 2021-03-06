package upmc.cigcount.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.CreatePackActivity;
import upmc.cigcount.MainActivity;
import upmc.cigcount.adapters.PacksAdapter;
import upmc.cigcount.R;
import upmc.cigcount.model.Pack;
import upmc.cigcount.model.User;

/**
 * Dialog fragment which displays packs list
 * Allows to choose one pack to be the current pack
 */
public class ChangePackFragment extends DialogFragment {

    private ListView packsList;
    private TextView noPackText;
    private PacksAdapter adapter;
    private User user;

    public ChangePackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_change_pack, null);
        packsList = (ListView) v.findViewById(R.id.packsList);
        noPackText = (TextView) v.findViewById(R.id.noPackText);
        user = CigCountApplication.getInstance().user();
        adapter = new PacksAdapter(getContext(), user.packs());

        builder.setView(v);
        builder.setTitle(R.string.change_pack_fragment_title);
        setList();

        builder.setPositiveButton(R.string.change_pack_fragment_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getContext(), CreatePackActivity.class));
            }
        })
        .setNegativeButton(R.string.change_pack_fragment_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ChangePackFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    /**
     * Check if there are packs and show the list
     * Clicked pack become the new current pack
     */
    private void setList() {
        if(user.packs().isEmpty()) {
            noPackText.setText(R.string.change_pack_fragment_empty);
        } else {
            packsList.setAdapter(adapter);
            registerForContextMenu(packsList);

            packsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pack selectedPack = user.packs().get(position);

                    user.setCurrentPack(selectedPack);
                    CigCountApplication.getInstance().saveData();
                    ((MainActivity) getActivity()).setCurrentPack();
                    ChangePackFragment.this.getDialog().cancel();
                }
            });
        }
    }

}
