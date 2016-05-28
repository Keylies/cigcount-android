package upmc.cigcount;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.File;
import java.io.FileInputStream;

import upmc.cigcount.model.User;

/**
 * Application class which manage CigCount data
 * Load and save user data from internal storage
 */
public class CigCountApplication extends Application {

    private User user;
    static CigCountApplication singleton;
    private final String DATA_FILE = "cigcount_data";

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        loadUser();
    }

    static public CigCountApplication getInstance() {
        return singleton;
    }

    public User user() {
        return user;
    }

    /**
     * Empty user's cigarettes and save data
     */
    public void resetCigs() {
        user.resetCigs();
        saveData();
    }

    /**
     * Empty user's packs and save data
     */
    public void resetPacks() {
        user.resetPacks();
        saveData();
    }

    /**
     * Create a new empty user and save data
     */
    public void resetData() {
        user = new User();
        saveData();
    }

    /**
     * Serialize user object to JSON into an internal file
     */
    public void saveData() {
        try {
            JsonWriter jw = new JsonWriter(openFileOutput(DATA_FILE, Context.MODE_PRIVATE));
            jw.write(user);
            jw.close();
        } catch(Exception e) {
            Log.i("Exception :", e.toString());
        }
    }

    /**
     * Test if internal data file exists to load data
     * If not, create a empty user
     */
    private void loadUser() {
        File dataFile = new File(getFilesDir().getPath() + "/" + DATA_FILE);

        if(dataFile.exists()) {
            loadData();
        } else { // First time CigCount is launched
            user = new User();
        }
    }

    /**
     * Unserialize JSON from internal file to user object
     */
    private void loadData() {
        FileInputStream fis;

        try {
            fis = openFileInput(DATA_FILE);
            user = (User) new JsonReader(fis).readObject();
            fis.close();
        } catch(Exception e) {
            Log.i("Exception :", e.toString());
        }
    }
}
