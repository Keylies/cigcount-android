package upmc.cigcount.model;

import android.util.Log;

import java.util.ArrayList;

import upmc.cigcount.CigCountApplication;

/**
 * Created by Clément on 16/05/2016.
 */
public class User {
    private ArrayList<Cigarette> cigSmoked;
    private ArrayList<Pack> packs;
    private Pack currentPack;

    public User() {
        cigSmoked = new ArrayList<Cigarette>();
        packs = new ArrayList<Pack>();
        currentPack = null;
    }

    public boolean addCigarette() {
        return currentPack == null ? false : cigSmoked.add(new Cigarette(currentPack));
    }

    public void addPack(Pack newPack) {
        packs.add(newPack);
        currentPack = newPack;
    }

    public int cigNumber() {
        return cigSmoked.size();
    }

    public ArrayList<Cigarette> cigSmoked() {
        return cigSmoked;
    }

    public String currentPack() {

        return currentPack != null ? currentPack.brand() : "Choose a Pack";
    }

    public void setCurrentPack(Pack pack) {
        this.currentPack = pack;
    }

    public ArrayList<Pack> packs() {
        return packs;
    }

    public void deletePack(int position) {
        Pack packToDelete = packs.get(position);

        if (packToDelete == currentPack)
            currentPack = null;
        packs.remove(packToDelete);
    }
}
