package upmc.cigcount.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.MainActivity;
import upmc.cigcount.ManagePacksActivity;
import upmc.cigcount.R;
import upmc.cigcount.model.Pack;

public class EditPackFragment extends DialogFragment {

    private int position;
    private Pack pack;
    private View v;
    private EditText brand;
    private EditText cigNb;
    private EditText price;
    private EditText tobaccoRate;
    private EditText paperRate;
    private EditText agentsRate;

    public EditPackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_edit_pack, null);

        Bundle args = getArguments();
        position = args.getInt("position");
        pack = CigCountApplication.getInstance().user().packs().get(position);

        builder.setView(v);
        builder.setTitle("Edit " + pack.brand());
        getPackDetails();

        builder.setPositiveButton(R.string.edit_pack_fragment_edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editPack();
                    }
                })
                .setNegativeButton(R.string.edit_pack_fragment_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditPackFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void getPackDetails() {
        brand = (EditText) v.findViewById(R.id.editBrand);
        cigNb = (EditText) v.findViewById(R.id.editCig);
        price = (EditText) v.findViewById(R.id.editPrice);
        tobaccoRate = (EditText) v.findViewById(R.id.editTobaccoRate);
        paperRate = (EditText) v.findViewById(R.id.editPaperRate);
        agentsRate = (EditText) v.findViewById(R.id.editAgentsRate);

        brand.setText(pack.brand());
        cigNb.setText(pack.nbCigarettes());
        price.setText(pack.price());
        tobaccoRate.setText(pack.tobaccoRate());
        paperRate.setText(pack.paperRate());
        agentsRate.setText(pack.agentsRate());
    }

    private void editPack() {
        if (allIsFilled()) {
            pack.editPack(
                brand.getText().toString(),
                Integer.parseInt(cigNb.getText().toString()),
                Float.valueOf(price.getText().toString()),
                Float.valueOf(tobaccoRate.getText().toString()),
                Float.valueOf(paperRate.getText().toString()),
                Float.valueOf(agentsRate.getText().toString()),
                new HashMap<String, Float>());
            ((ManagePacksActivity) getActivity()).refreshAdapter();
            CigCountApplication.getInstance().saveData();
        }
        else
            Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
    }

    private Boolean allIsFilled() {
        return !isEmpty(brand) && !isEmpty(cigNb) && !isEmpty(price) && !isEmpty(tobaccoRate) && !isEmpty(paperRate) && !isEmpty(agentsRate);
    }

    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }
}
