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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import upmc.cigcount.model.Cigarette;
import upmc.cigcount.model.Pack;
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

    public User user() {
        return user;
    }

    public void resetData() {
        user = new User();
        saveData();
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

    private void loadUser() {
        File dataFile = new File(getFilesDir().getPath() + "/" + DATA_FILE);

        if(dataFile.exists()) {
            loadData();
        } else { // First time CigCount is launched
            user = new User();
        }
    }

    private void loadData() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(DATA_FILE);
            user = gson.fromJson(new BufferedReader(new InputStreamReader(fis)), User.class);
            Log.i("user", user.toString());
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

    private void addTestData() {
        Pack newPack = new Pack("test", 20, 20, 20, 20, 20, new HashMap<String, Float>());
        user.addPack(newPack);
        user.setCurrentPack(newPack);

        for(int i = 1; i <= 3; i++) {
            user.addPack(new Pack("test" + i, 20, 20, 20, 20, 20, new HashMap<String, Float>()));
        }

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        for(int i = 1; i <= 70; i++) {
            c.add(Calendar.DATE, i);
            for(int j = 0; j < 3; j++) {
                user.cigSmoked().add(new Cigarette(newPack, c.getTime()));
            }
        }

    }
}
