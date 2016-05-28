package upmc.cigcount.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.R;
import upmc.cigcount.model.Cigarette;
import upmc.cigcount.model.Pack;

/**
 * Fragment which displays textual information for all packs or a particular one
 */
public class TextStatsFragment extends Fragment {

    private int page;
    private Pack selectedPack;
    private ArrayList<Cigarette> cigSmoked;
    private ArrayList<Cigarette> textCigSmoked;
    private TextView cigSmokedTotalValue;
    private TextView lifeLossTotalValue;
    private TextView moneyLossTotalValue;
    private TextView tobaccoSmokedTotalValue;
    private TextView paperBurntTotalValue;
    private TextView agentsSmokedTotalValue;

    public static TextStatsFragment newInstance(int page) {
        TextStatsFragment textFragment = new TextStatsFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        textFragment.setArguments(args);
        return textFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page");
        selectedPack = null;
        cigSmoked = CigCountApplication.getInstance().user().cigSmoked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_stats, container, false);
        cigSmokedTotalValue = (TextView) view.findViewById(R.id.cigSmokedTotalValue);
        lifeLossTotalValue = (TextView) view.findViewById(R.id.lifeLossTotalValue);
        moneyLossTotalValue = (TextView) view.findViewById(R.id.moneyLossTotalValue);
        tobaccoSmokedTotalValue = (TextView) view.findViewById(R.id.tobaccoSmokedTotalValue);
        paperBurntTotalValue = (TextView) view.findViewById(R.id.paperBurntTotalValue);
        agentsSmokedTotalValue = (TextView) view.findViewById(R.id.agentsSmokedTotalValue);

        setStats();

        return view;
    }

    /**
     * Update the selected pack and call for stats refresh
     * @param selectedPack
     */
    public void updateStats(Pack selectedPack) {
        this.selectedPack = selectedPack;

        setStats();
    }

    /**
     * Fill stats textViews
     */
    private void setStats() {
        textCigSmoked = selectedPack != null ? cigsByPack() : cigSmoked;

        cigSmokedTotalValue.setText(String.valueOf(textCigSmoked.size()));
        lifeLossTotalValue.setText(String.valueOf(getLifeLoss()));
        moneyLossTotalValue.setText(String.valueOf(getMoneyLoss()));
    }

    /**
     * Get the number of minutes of life lost
     * @return the number of minutes
     */
    private int getLifeLoss() {
        return textCigSmoked.size() * 11;
    }

    /**
     * Get the amount of money lost
     * @return the amount of money
     */
    private float getMoneyLoss() {
        float moneyLoss = 0;

        for(Cigarette c : textCigSmoked)
            moneyLoss += getSingleCigPrice(c.pack());

        return moneyLoss;
    }

    /**
     * Get a price for a single cigarette for a particular pack
     * @param pack the selected pack
     * @return the price of a cigarette
     */
    private float getSingleCigPrice(Pack pack) {
        return pack.singleCigPrice();
    }

    /**
     * Get all cigarettes which were added with the selectedPack
     * @return the list of cigarettes
     */
    private ArrayList<Cigarette> cigsByPack() {
        ArrayList<Cigarette> filteredCigs = new ArrayList<>();

        for(Cigarette c : cigSmoked)
            if (c.pack() == selectedPack)
                filteredCigs.add(c);

        return filteredCigs;
    }
}
