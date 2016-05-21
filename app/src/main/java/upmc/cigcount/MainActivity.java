package upmc.cigcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    TextView currentPack;
    TextView cigNumber;
    CigCountApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = CigCountApplication.getInstance();
        currentPack = (TextView) findViewById(R.id.textCurrentPack);
        cigNumber = (TextView) findViewById(R.id.textCigNumber);
        currentPack.setText(app.user().currentPack());
        setCigNumber();
    }

    public void addCig(View view) {
        if (!app.user().addCigarette()) {
            startActivity(new Intent(this, CreatePackActivity.class));
        } else {
            app.saveData();
            setCigNumber();
        }
    }

    private void setCigNumber() {
        cigNumber.setText(String.valueOf(app.user().cigNumber()));
    }
}
