package upmc.cigcount.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import upmc.cigcount.ManagePacksActivity;
import upmc.cigcount.R;

/**
 * Show confirmation window before delete packs
 */
public class DeletePackFragment extends DialogFragment {

    public DeletePackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_pack_fragment_message);
        builder.setPositiveButton(R.string.delete_pack_fragment_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ((ManagePacksActivity) getActivity()).deleteSelectedPacks();
            }
        })
        .setNegativeButton(R.string.delete_pack_fragment_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DeletePackFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
