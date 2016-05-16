package upmc.cigcount.model;

import java.util.Date;

/**
 * Created by Clément on 16/05/2016.
 */
public class Cigarette {
    private final Date DATE;
    private Pack pack;

    public Cigarette(Pack pack) {
        DATE = new Date();
        this.pack = pack;
    }
}
