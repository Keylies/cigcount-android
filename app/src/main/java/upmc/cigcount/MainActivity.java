package upmc.cigcount;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import upmc.cigcount.fragments.ChangePackFragment;

/**
 * Main activity which displays information,
 * allows to add a cigarette and quickly change current pack
 */
public class MainActivity extends BaseActivity {

    TextView currentPack;
    TextView cigNumber;
    CigCountApplication app;
    ChangePackFragment changeFragment;

    private static final Random RANDOM = new Random();
    private static final String[] WARNINGS = {
            "Dont do this!",
            "Sport may be the solution",
            "Oups, you lost 11 minutes of life",
            "That one wasn't great, right?",
            "You could've bought a private jet so far",
            "Nice breath man",
            "Mix it with coffee",
            "At least it's only tobacco ;)",
            "Rolled ones are cheaper",
            "So you like tar this much?"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = CigCountApplication.getInstance();
        currentPack = (TextView) findViewById(R.id.textCurrentPack);
        cigNumber = (TextView) findViewById(R.id.textCigNumber);
        changeFragment = new ChangePackFragment();
        setCurrentPack();
        setCigNumber();
    }

    /**
     * Add a cigarette for the current pack,
     * if no current pack is set, open packs list
     * @param view
     */
    public void addCig(View view) {
        if (!app.user().addCigarette()) {
            changeFragment.show(getSupportFragmentManager(), "change");
        } else {
            app.saveData();
            setCigNumber();
            Toast.makeText(getApplicationContext(), WARNINGS[RANDOM.nextInt(WARNINGS.length)],
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Open packs list
     * @param view
     */
    public void changePack(View view) {
        changeFragment.show(getSupportFragmentManager(), "change");
    }

    /**
     * Show the total number of smoked cigarettes
     */
    private void setCigNumber() {
        cigNumber.setText(String.valueOf(app.user().cigNumber()));
    }

    /**
     * Show the current pack
     */
    public void setCurrentPack() {
        currentPack.setText(app.user().currentPack());
    }
}
