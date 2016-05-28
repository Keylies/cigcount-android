package upmc.cigcount.model;

import java.util.ArrayList;

public class User {
    private ArrayList<Cigarette> cigSmoked;
    private ArrayList<Pack> packs;
    private Pack currentPack;

    public User() {
        cigSmoked = new ArrayList<Cigarette>();
        packs = new ArrayList<Pack>();
        currentPack = null;
    }

    /**
     * Check if a current pack is defined before adding a cigarette
     * @return true if a cigarette was successfully added
     */
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

    public void resetCigs() {
        cigSmoked = new ArrayList<>();
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

    public void resetPacks() {
        packs = new ArrayList<>();
        currentPack = null;
    }

    /**
     * Delete a pack
     * If deleted pack is the current pack, current pack is set to null
     * @param position pack position in the list
     */
    public void deletePack(int position) {
        Pack packToDelete = packs.get(position);

        if (packToDelete == currentPack)
            currentPack = null;
        packs.remove(packToDelete);
    }
}
