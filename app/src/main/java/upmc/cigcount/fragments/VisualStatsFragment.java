package upmc.cigcount.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import upmc.cigcount.CigCountApplication;
import upmc.cigcount.R;
import upmc.cigcount.model.Cigarette;
import upmc.cigcount.model.Pack;

/**
 * Fragment which displays visual information for all packs or a particular one
 */
public class VisualStatsFragment extends Fragment {

    private int page;
    private HorizontalBarChart chart;
    private Pack selectedPack;
    private ArrayList<Cigarette> cigSmoked;

    public static VisualStatsFragment newInstance(int page) {
        VisualStatsFragment visualFragment = new VisualStatsFragment();
        Bundle args = new Bundle();

        args.putInt("page", page);
        visualFragment.setArguments(args);

        return visualFragment;
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
        View view = inflater.inflate(R.layout.fragment_visual_stats, container, false);
        chart = (HorizontalBarChart) view.findViewById(R.id.chart);

        setChartData();
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.animateXY(500, 500);

        return view;
    }

    /**
     * Fill the chart with numbers of cigarettes smoked per date
     * For all packs or a particular one
     */
    private void setChartData() {
        ArrayList<Cigarette> chartCigSmoked = selectedPack != null ? cigsByPack() : cigSmoked;

        if (!chartCigSmoked.isEmpty()) {
            ArrayList<String> yDates = new ArrayList<>();
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            ArrayList<BarEntry> xNumbers = new ArrayList<>();
            String date;
            int count;

            date = chartCigSmoked.get(0).smokedDate();
            count = 1;

            yDates.add(date);
            for (int i = 1; i < chartCigSmoked.size(); i++) {
                if (!date.equals(chartCigSmoked.get(i).smokedDate())) {
                    xNumbers.add(new BarEntry(count, yDates.size() - 1));
                    count = 1;
                    date = chartCigSmoked.get(i).smokedDate();
                    yDates.add(date);
                }
                count++;
            }
            xNumbers.add(new BarEntry(count, yDates.size() - 1));

            dataSets.add(new BarDataSet(xNumbers, "Cigarettes smoked"));
            refresh(new BarData(yDates, dataSets));
        } else
            chart.clear();
    }

    /**
     * Update the selected pack and call for stats refresh
     * @param selectedPack
     */
    public void updateStats(Pack selectedPack) {
        this.selectedPack = selectedPack;

        setChartData();
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

    /**
     * Refresh the chart with new data
     * @param data new data
     */
    private void refresh(BarData data) {
        chart.setData(data);
        chart.invalidate();
    }
}
