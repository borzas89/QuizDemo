package hu.zsoltborza.quizdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.zsoltborza.quizdemo.R;

/**
 * Created by Borzas on 2017. 02. 19..
 */

public class SettingsActivity extends AppCompatActivity{


    public EditText etValue;
    public Button btnSave;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        etValue = (EditText) findViewById(R.id.etValue);


        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!"".equals(etValue.getText().toString())) {


                    Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();

                    Intent mainIntent;
                    mainIntent = new Intent(SettingsActivity.this
                            , PagerActivity.class);
                    mainIntent.putExtra("SETTINGS", etValue.getText().toString());
                    startActivity(mainIntent);

                } else {
                    etValue.setError("Type the number of questions!!");
                }


            }
        });



    }
}
