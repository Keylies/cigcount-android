package upmc.cigcount;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import upmc.cigcount.model.Cigarette;

public class StatsActivity extends BaseActivity {

    private HorizontalBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        chart = (HorizontalBarChart) findViewById(R.id.chart);

        setChartData();
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.animateXY(500, 500);

    }

    private void setChartData() {
        ArrayList<Cigarette> cigSmoked = CigCountApplication.getInstance().user().cigSmoked();
        ArrayList<String> yDates = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> xNumbers = new ArrayList<>();
        String date = cigSmoked.get(0).smokedDate();
        int count = 1;

        yDates.add(date);
        for(int i = 1; i < cigSmoked.size(); i++) {
            if ( !date.equals(cigSmoked.get(i).smokedDate()) ) {
                xNumbers.add(new BarEntry(count, yDates.size() - 1));
                count = 1;
                date = cigSmoked.get(i).smokedDate();
                yDates.add(date);
            }
            count++;
        }
        xNumbers.add(new BarEntry(count - 1, yDates.size() - 1));

        BarDataSet dataSet = new BarDataSet(xNumbers, "Cigarettes smoked");
        dataSets.add(dataSet);
        BarData data = new BarData(yDates, dataSets);
        chart.setData(data);
        chart.invalidate();
    }
}
