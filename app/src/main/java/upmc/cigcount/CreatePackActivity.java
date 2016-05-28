package upmc.cigcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import upmc.cigcount.model.Pack;

/**
 * Activity which allow to add a new pack
 */
public class CreatePackActivity extends BaseActivity {

    private EditText brand;
    private EditText cigNb;
    private EditText price;
    private EditText tobaccoRate;
    private EditText paperRate;
    private EditText agentsRate;
    private CigCountApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pack);
        app = CigCountApplication.getInstance();
        setIHM();
    }

    /**
     * Instantiate graphical elements
     */
    private void setIHM() {
        brand = (EditText)findViewById(R.id.editBrand);
        cigNb = (EditText)findViewById(R.id.editCig);
        price = (EditText)findViewById(R.id.editPrice);
        tobaccoRate = (EditText)findViewById(R.id.editTobaccoRate);
        paperRate = (EditText)findViewById(R.id.editPaperRate);
        agentsRate = (EditText)findViewById(R.id.editAgentsRate);
    }

    /**
     * Get values from editTexts and call for pack creation
     */
    public void addPack(View view) {
        if (allIsFilled()) {
            if (!brandExists()) {
                Pack newPack = new Pack(
                        brand.getText().toString(),
                        Integer.parseInt(cigNb.getText().toString()),
                        Float.valueOf(price.getText().toString()),
                        Float.valueOf(tobaccoRate.getText().toString()),
                        Float.valueOf(paperRate.getText().toString()),
                        Float.valueOf(agentsRate.getText().toString()),
                        new HashMap<String, Float>());

                app.user().addPack(newPack);
                app.saveData();
                startActivity(new Intent(this, MainActivity.class));
            } else
                Toast.makeText(this, R.string.same_brand, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, R.string.not_filled, Toast.LENGTH_SHORT).show();
    }

    /**
     * Check if all editTexts are filled
     * @return true if all editTexts are not empty, false if not
     */
    private Boolean allIsFilled() {
        return !isEmpty(brand) && !isEmpty(cigNb) && !isEmpty(price) && !isEmpty(tobaccoRate) && !isEmpty(paperRate) && !isEmpty(agentsRate);
    }

    /**
     * Check if an editText field is empty
     * @param editText an editText field
     * @return true if there is not editText content
     */
    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }

    /**
     * Check if a pack with a same brand has been created
     * @return true if a same pack's brand already exists
     */
     private Boolean brandExists() {
         String currentBrand = brand.getText().toString();
         for(Pack p : app.user().packs())
             if (currentBrand.equals(p.brand()))
                 return true;
         return false;
     }
}
