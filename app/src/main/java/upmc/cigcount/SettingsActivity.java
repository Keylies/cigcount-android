package upmc.cigcount;

import android.os.Bundle;
import android.view.View;

/**
 * Allows to perform global actions on CigCount
 */
public class SettingsActivity extends BaseActivity {

    private CigCountApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        app = CigCountApplication.getInstance();
    }

    /**
     * Call the cigarettes reset
     * @param view
     */
    public void resetCigs(View view) {
        app.resetCigs();
    }

    /**
     * Call the packs reset
     * @param view
     */
    public void resetPacks(View view) {
        app.resetPacks();
    }

    /**
     * Call the user reset
     * @param view
     */
    public void resetData(View view) {
        app.resetData();
    }
}
