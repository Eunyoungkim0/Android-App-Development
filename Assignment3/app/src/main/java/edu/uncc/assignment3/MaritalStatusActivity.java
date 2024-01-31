package edu.uncc.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MaritalStatusActivity extends AppCompatActivity {

    RadioGroup radioMarital;
    static public final String USER_MARITALSTATUS = "MARITALSTATUS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marital_status);

        radioMarital = findViewById(R.id.radioMarital);

        findViewById(R.id.btnMrtCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnMrtSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int marital_status = radioMarital.getCheckedRadioButtonId();
                String str_marital_status = "";
                if(marital_status == R.id.radioNotMarried){
                    str_marital_status = getString(R.string.mrt_not_married);
                } else if(marital_status == R.id.radioMarried){
                    str_marital_status = getString(R.string.mrt_married);
                } else if(marital_status == R.id.radioMrtPreferNot){
                    str_marital_status = getString(R.string.prefer_not_to_say);
                }

                if(str_marital_status.isEmpty()){
                    Toast.makeText(MaritalStatusActivity.this, "Select Marital Status", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(USER_MARITALSTATUS, str_marital_status);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}