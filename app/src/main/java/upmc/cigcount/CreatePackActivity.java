package upmc.cigcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import upmc.cigcount.model.Pack;

public class CreatePackActivity extends BaseActivity {

    EditText brand;
    EditText cigNb;
    EditText price;
    EditText tobaccoRate;
    EditText paperRate;
    EditText agentsRate;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pack);

        setIHM();
    }

    private void setIHM() {
        brand = (EditText)findViewById(R.id.editBrand);
        cigNb = (EditText)findViewById(R.id.editCig);
        price = (EditText)findViewById(R.id.editPrice);
        tobaccoRate = (EditText)findViewById(R.id.editTobaccoRate);
        paperRate = (EditText)findViewById(R.id.editPaperRate);
        agentsRate = (EditText)findViewById(R.id.editAgentsRate);
        // TODO : ajouter système d'ajout de composants persos avec un layout prédéfini
        addButton = (Button)findViewById(R.id.buttonAddPack);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPack();
            }
        });
    }

    private void addPack() {
        CigCountApplication app = CigCountApplication.getInstance();
        if (allIsFilled()) {
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
            // TODO : envoyer message (Toast) sur la main de confirmation d'ajout
            startActivity(new Intent(this, MainActivity.class));
        }
        else
            Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
    }

    private Boolean allIsFilled() {
        return !isEmpty(brand) && !isEmpty(cigNb) && !isEmpty(price) && !isEmpty(tobaccoRate) && !isEmpty(paperRate) && !isEmpty(agentsRate);
    }

    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }
}
