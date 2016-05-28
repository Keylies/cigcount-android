package upmc.cigcount.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cigarette {
    private Date date;
    private Pack pack;

    public Cigarette(Pack pack) {
        date = new Date();
        this.pack = pack;
    }

    /**
     * Get the formatted date when the cigarette was smoked
     * @return the formatted date
     */
    public String smokedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    public Pack pack() {
        return pack;
    }
}
