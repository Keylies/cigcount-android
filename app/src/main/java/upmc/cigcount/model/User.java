package upmc.cigcount.model;

import java.util.ArrayList;

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
    }

    public void addCigarette() {
        cigSmoked.add(new Cigarette(currentPack));
    }
}
