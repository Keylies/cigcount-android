package upmc.cigcount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

import upmc.cigcount.model.User;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "upmc.cigcount.MESSAGE";
    private final String DATA_FILE = "cigcount_data";
    private Gson gson;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        getUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileOutputStream fos = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fos.write(gson.toJson(user).getBytes());
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void getUser() {
        File file = new File(DATA_FILE);

        if(file.exists())
            user = gson.fromJson(DATA_FILE, User.class);
        else { // First visit
            Log.v("test", "test");
            /*Intent intent = new Intent(this, CreatePack.class);
            startActivity(intent);*/
        }
    }
}
