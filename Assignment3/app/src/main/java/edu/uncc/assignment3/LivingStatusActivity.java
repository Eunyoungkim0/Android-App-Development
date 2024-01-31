package edu.uncc.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LivingStatusActivity extends AppCompatActivity {

    RadioGroup radioLivingStatus;
    static public final String USER_LIVING_STATUS = "LIVING_STATUS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_status);
        radioLivingStatus = findViewById(R.id.radioLivingStatus);

        findViewById(R.id.btnLSCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.btnLSSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int living_status = radioLivingStatus.getCheckedRadioButtonId();
                String str_living_status = "";
                if(living_status == R.id.radioHomeOwner){
                    str_living_status = getString(R.string.liv_homeowner);
                } else if(living_status == R.id.radioRenter){
                    str_living_status = getString(R.string.liv_renter);
                } else if(living_status == R.id.radioLessee){
                    str_living_status = getString(R.string.liv_lessee);
                } else if(living_status == R.id.radioOther){
                    str_living_status = getString(R.string.liv_other);
                } else if(living_status == R.id.radioLSPreferNot){
                    str_living_status = getString(R.string.prefer_not_to_say);
                }

                if(str_living_status.isEmpty()){
                    Toast.makeText(LivingStatusActivity.this, "Select Living Status", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(USER_LIVING_STATUS, str_living_status);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}