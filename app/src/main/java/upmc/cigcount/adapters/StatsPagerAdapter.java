package upmc.cigcount.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import upmc.cigcount.fragments.TextStatsFragment;
import upmc.cigcount.fragments.VisualStatsFragment;
import upmc.cigcount.model.Pack;

/**
 * Allows to navigate between visual and textual stats with tabs
 */
public class StatsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Visual", "Text" };
    private Context context;
    private VisualStatsFragment visualFragment;
    private TextStatsFragment textFragment;

    public StatsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        visualFragment = VisualStatsFragment.newInstance(0);
        textFragment = TextStatsFragment.newInstance(1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return visualFragment;
            case 1:
                return textFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    /**
     * Relay between the activity and its fragments
     * Call for fragments content update
     * @param selectedPack the new selectedPack
     */
    public void updateStats(Pack selectedPack) {
        visualFragment.updateStats(selectedPack);
        textFragment.updateStats(selectedPack);
    }
}
