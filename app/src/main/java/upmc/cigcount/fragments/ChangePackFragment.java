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

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.CreatePackActivity;
import upmc.cigcount.MainActivity;
import upmc.cigcount.PacksAdapter;
import upmc.cigcount.R;
import upmc.cigcount.model.Pack;
import upmc.cigcount.model.User;

public class ChangePackFragment extends DialogFragment {

    private View v;
    private ListView packsList;
    private PacksAdapter adapter;
    private User user;

    public ChangePackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_change_pack, null);
        packsList = (ListView) v.findViewById(R.id.packsList);
        user = CigCountApplication.getInstance().user();
        adapter = new PacksAdapter(getContext(), user.packs());

        builder.setView(v);
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

    private void setList() {
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
