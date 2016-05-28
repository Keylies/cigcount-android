package upmc.cigcount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Base activity which set up CigCount menu
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.actionHome:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.actionAddPack:
                startActivity(new Intent(this, CreatePackActivity.class));
                return true;
            case R.id.actionChoosePack:
                startActivity(new Intent(this, ManagePacksActivity.class));
                return true;
            case R.id.actionStats:
                startActivity(new Intent(this, StatsActivity.class));
                return true;
            case R.id.actionSettings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
