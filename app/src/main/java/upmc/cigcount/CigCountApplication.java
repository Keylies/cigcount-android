package upmc.cigcount;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import upmc.cigcount.model.User;

/**
 * Created by Clément on 21/05/2016.
 */
public class CigCountApplication extends Application {

    private User user;
    static CigCountApplication singleton;
    private final String DATA_FILE = "cigcount_data";
    private Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        loadUser();
    }

    static public CigCountApplication getInstance() {
        return singleton;
    }

    private void loadUser() {
        File dataFile = new File(getFilesDir().getPath() + "/" + DATA_FILE);

        if(dataFile.exists()) {
            loadData();
        } else { // First time CigCount is launched
            user = new User();
        }
    }

    public void saveData() {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fos.write(gson.toJson(user).getBytes());
            fos.close();
        } catch(Exception e) {
            Log.i("Exception :", e.toString());
        }
    }

    private void loadData() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(DATA_FILE);
            user = gson.fromJson(new BufferedReader(new InputStreamReader(fis)), User.class);
            fis.close();
        } catch(Exception e) {
            Log.i("Exception :", e.toString());
        }
    }

    private void readData() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(DATA_FILE);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String test;
            while ((test = bufferedReader.readLine()) != null) {
                stringBuffer.append(test);
            }
            Log.i("Read File Test", stringBuffer.toString());
            fis.close();
        } catch(Exception e) {
            Log.i("Exception :", e.toString());
        }
    }
}
