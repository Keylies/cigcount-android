package upmc.cigcount;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import upmc.cigcount.adapters.StatsPagerAdapter;
import upmc.cigcount.fragments.StatsChangePackFragment;
import upmc.cigcount.model.Pack;

/**
 * Show visual and textual statistics for all packs or a particular one
 */
public class StatsActivity extends BaseActivity {

    private TextView currentPackText;
    private Pack currentPack;
    private StatsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        currentPackText = (TextView) findViewById(R.id.textCurrentPack);
        currentPack = null;
        adapter = new StatsPagerAdapter(getSupportFragmentManager(), this);

        currentPackText.setText(R.string.all_packs_text);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * Open packs list
     * @param view
     */
    public void changePack(View view) {
        StatsChangePackFragment changeFragment = new StatsChangePackFragment();
        changeFragment.show(getSupportFragmentManager(), "change");
    }

    /**
     * Set the new current pack and call for stats update
     * @param pack the new selected pack
     */
    public void setCurrentPack(Pack pack) {
        currentPack = pack;
        adapter.updateStats(currentPack);
        currentPackText.setText(currentPack.brand());
    }

    /**
     * Call for stats update with all packs
     * @param view
     */
    public void allPacks(View view) {
        currentPack = null;
        adapter.updateStats(currentPack);
        currentPackText.setText(R.string.all_packs_text);
    }

}
