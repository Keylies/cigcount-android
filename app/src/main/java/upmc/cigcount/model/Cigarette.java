package upmc.cigcount.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Clément on 16/05/2016.
 */
public class Cigarette {
    private Date date;
    private Pack pack;

    public Cigarette(Pack pack) {
        date = new Date();
        this.pack = pack;
    }

    public Cigarette(Pack pack, Date date) {
        this.date = date;
        this.pack = pack;
    }

    public String smokedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
