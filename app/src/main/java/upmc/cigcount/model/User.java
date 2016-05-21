package upmc.cigcount.model;

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

    public String currentPack() {
        return currentPack.brand();
    }
}
