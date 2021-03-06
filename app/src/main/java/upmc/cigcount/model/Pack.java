package upmc.cigcount.model;

import java.util.HashMap;

public class Pack {
    private static int last_id;
    private int id;
    private String brand;
    private int nbCigarettes;
    private float price;
    private float tobaccoRate;
    private float paperRate;
    private float agentsRate;
    private HashMap<String, Float> components;
    private final float WEIGHT = 0.85f; // Gram

    public Pack(String brand, int nbCigarettes, float price, float tobaccoRate, float paperRate, float agentsRate, HashMap<String, Float> components){
        id = last_id++;
        this.brand = brand;
		this.nbCigarettes = nbCigarettes;
        this.price = price;
        this.tobaccoRate = tobaccoRate;
        this.paperRate = paperRate;
        this.agentsRate = agentsRate;
        this.components = components;
    }

    public String brand() {
        return brand;
    }

    public String nbCigarettes() {
        return String.valueOf(nbCigarettes);
    }

    public String price() {
        return String.valueOf(price);
    }

    public String tobaccoRate() {
        return String.valueOf(tobaccoRate);
    }

    public String paperRate() {
        return String.valueOf(paperRate);
    }

    public String agentsRate() {
        return String.valueOf(agentsRate);
    }

    public void editPack(String brand, int nbCigarettes, float price, float tobaccoRate, float paperRate, float agentsRate, HashMap<String, Float> components) {
        this.brand = brand;
        this.nbCigarettes = nbCigarettes;
        this.price = price;
        this.tobaccoRate = tobaccoRate;
        this.paperRate = paperRate;
        this.agentsRate = agentsRate;
        this.components = components;
    }

    public float singleCigPrice() {
        return price / nbCigarettes;
    }

    /**
     * Get tobacco rate as gram in a cigarette
     * @return tobacco as gram in a single cigarette
     */
    public float singleCigTobacco() {
        return (tobaccoRate * WEIGHT) / 100;
    }

    /**
     * Get paper rate as gram in a cigarette
     * @return paper as gram in a single cigarette
     */
    public float singleCigPaper() {
        return (paperRate * WEIGHT) / 100;
    }

    /**
     * Get agents rate as gram in a cigarette
     * @return agents as gram in a single cigarette
     */
    public float singleCigAgents() {
        return (agentsRate * WEIGHT) / 100;
    }

}
