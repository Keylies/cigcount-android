package upmc.cigcount;

import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void resetData(View view) {
        CigCountApplication.getInstance().resetData();
    }
}
