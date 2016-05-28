package upmc.cigcount.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.ManagePacksActivity;
import upmc.cigcount.R;
import upmc.cigcount.model.Pack;

/**
 * Dialog fragment which displays pack information and allows to edit
 */
public class EditPackFragment extends DialogFragment {

    private Pack pack;
    private View v;
    private EditText brand;
    private EditText cigNb;
    private EditText price;
    private EditText tobaccoRate;
    private EditText paperRate;
    private EditText agentsRate;
    private CigCountApplication app;

    public EditPackFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_edit_pack, null);

        Bundle args = getArguments();
        int position = args.getInt("position");
        app = CigCountApplication.getInstance();
        pack = app.user().packs().get(position);

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

    /**
     * Instantiate graphical elements
     */
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

    /**
     * Get values from editTexts and call for pack edition
     */
    private void editPack() {
        if (allIsFilled()) {
            if (!brandExists()) {
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
            } else
                Toast.makeText(getContext(), R.string.same_brand, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(), R.string.not_filled, Toast.LENGTH_SHORT).show();
    }

    /**
     * Check if all editTexts are filled
     * @return true if all editTexts are not empty, false if not
     */
    private Boolean allIsFilled() {
        return !isEmpty(brand) && !isEmpty(cigNb) && !isEmpty(price) && !isEmpty(tobaccoRate) && !isEmpty(paperRate) && !isEmpty(agentsRate);
    }

    /**
     * Check if an editText field is empty
     * @param editText an editText field
     * @return true if there is not editText content
     */
    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }

    /**
     * Check if a pack with a same brand has been created
     * @return true if a same pack's brand already exists
     */
    private Boolean brandExists() {
        String currentBrand = brand.getText().toString();
        for(Pack p : app.user().packs())
            if (currentBrand.equals(p.brand()))
                return true;
        return false;
    }
}
