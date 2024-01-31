package edu.uncc.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EducationActivity extends AppCompatActivity {
    RadioGroup radioSelectEducation;
    static public final String USER_EDUCATION = "EDUCATION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        radioSelectEducation = findViewById(R.id.radioSelectEducation);

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int education = radioSelectEducation.getCheckedRadioButtonId();
                String str_education = "";
                if(education == R.id.radioBelowHighSchool){
                    str_education = getString(R.string.edu_below_high_school);
                } else if(education == R.id.radioHighSchool){
                    str_education = getString(R.string.edu_high_school);
                } else if(education == R.id.radioBachelor){
                    str_education = getString(R.string.edu_bachelors_degree);
                } else if(education == R.id.radioMaster){
                    str_education = getString(R.string.edu_masters_degree);
                } else if(education == R.id.radioPhd){
                    str_education = getString(R.string.edu_phd_or_higher);
                } else if(education == R.id.radioTradeSchool){
                    str_education = getString(R.string.edu_trade_school);
                } else if(education == R.id.radioPreferNot){
                    str_education = getString(R.string.edu_prefer_not_to_say);
                }

                if(str_education.isEmpty()){
                    Toast.makeText(EducationActivity.this, "Select Education", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(USER_EDUCATION, str_education);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}