package upmc.cigcount.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, String> stats = getStats();

        cigSmokedTotalValue.setText(stats.get("cigSmoked"));
        lifeLossTotalValue.setText(stats.get("lifeLoss"));
        moneyLossTotalValue.setText(stats.get("moneyLoss") + "€");
        tobaccoSmokedTotalValue.setText(stats.get("tobaccoSmoked") + "g");
        paperBurntTotalValue.setText(stats.get("paperSmoked") + "g");
        agentsSmokedTotalValue.setText(stats.get("agentsSmoked") + "g");
    }

    /**
     * Get stats for smoked cigarettes by packs components
     * @return a HashMap of Strings with stats
     */
    private HashMap<String, String> getStats() {
        HashMap<String, String> stats = new HashMap<>();
        int cigSmoked = textCigSmoked.size();
        int lifeLoss = textCigSmoked.size() * 11;
        float moneyLoss = 0;
        float tobaccoSmoked = 0;
        float paperSmoked = 0;
        float agentsSmoked = 0;

        for(Cigarette c : textCigSmoked) {
            Pack p = c.pack();
            moneyLoss += p.singleCigPrice();
            tobaccoSmoked += p.singleCigTobacco();
            paperSmoked += p.singleCigPaper();
            agentsSmoked += p.singleCigAgents();
        }

        stats.put("cigSmoked", String.valueOf(cigSmoked));
        stats.put("lifeLoss", String.valueOf(lifeLoss));
        stats.put("moneyLoss", String.format("%.2f", moneyLoss));
        stats.put("tobaccoSmoked", String.format("%.2f", tobaccoSmoked));
        stats.put("paperSmoked", String.format("%.2f", paperSmoked));
        stats.put("agentsSmoked", String.format("%.2f", agentsSmoked));

        return stats;
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
