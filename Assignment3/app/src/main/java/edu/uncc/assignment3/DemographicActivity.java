package edu.uncc.assignment3;

import static edu.uncc.assignment3.IdentificationActivity.USER_IDENTIFICATION;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DemographicActivity extends AppCompatActivity {

    TextView userEducation, userMaritalStatus, userLivingStatus, userIncome;
    String str_education, str_marital_status, str_living_status, str_income;
    public static final String USER_PROFILE = "PROFILE";

    String name ="", email="";
    int role=0;

    ActivityResultLauncher<Intent> startSelectEducation = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                if(result.getData().getSerializableExtra(EducationActivity.USER_EDUCATION) != null){
                    str_education = result.getData().getSerializableExtra(EducationActivity.USER_EDUCATION).toString();
                    userEducation.setText(str_education);
                }
            } else {
                str_education = "";
                userEducation.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> startSelectMaritalStatus = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                if(result.getData().getSerializableExtra(MaritalStatusActivity.USER_MARITALSTATUS) != null){
                    str_marital_status = result.getData().getSerializableExtra(MaritalStatusActivity.USER_MARITALSTATUS).toString();
                    userMaritalStatus.setText(str_marital_status);
                }
            } else {
                str_marital_status = "";
                userMaritalStatus.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> startSelectLivingStatus = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                if(result.getData().getSerializableExtra(LivingStatusActivity.USER_LIVING_STATUS) != null){
                    str_living_status = result.getData().getSerializableExtra(LivingStatusActivity.USER_LIVING_STATUS).toString();
                    userLivingStatus.setText(str_living_status);
                }
            } else {
                str_living_status = "";
                userLivingStatus.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> startSelectIncome = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                if(result.getData().getSerializableExtra(IncomeActivity.USER_INCOME) != null){
                    str_income = result.getData().getSerializableExtra(IncomeActivity.USER_INCOME).toString();
                    userIncome.setText(str_income);
                }
            } else {
                str_income = "";
                userIncome.setText("N/A");
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);

        userEducation = findViewById(R.id.userEducation);
        userMaritalStatus = findViewById(R.id.userMaritalStatus);
        userLivingStatus = findViewById(R.id.userLivingStatus);
        userIncome = findViewById(R.id.userIncome);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_IDENTIFICATION)) {
            Identification identification = (Identification) intent.getSerializableExtra(USER_IDENTIFICATION);

            name = identification.getName();
            email = identification.getEmail();
            role = identification.getRole();
        }
        findViewById(R.id.btnEducation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, EducationActivity.class);
                startSelectEducation.launch(intent);
            }
        });

        findViewById(R.id.btnMaritalStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, MaritalStatusActivity.class);
                startSelectMaritalStatus.launch(intent);
            }
        });

        findViewById(R.id.radioLivingStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, LivingStatusActivity.class);
                startSelectLivingStatus.launch(intent);
            }
        });

        findViewById(R.id.btnIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, IncomeActivity.class);
                startSelectIncome.launch(intent);
            }
        });

        findViewById(R.id.btnDemoNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                boolean error = false;
                if(userEducation.getText().equals("N/A")){
                    message = "Select Education";
                    error = true;
                }else if(userMaritalStatus.getText().equals("N/A")){
                    message = "Select Marital Status";
                    error = true;
                }else if(userLivingStatus.getText().equals("N/A")){
                    message = "Select Living Status";
                    error = true;
                }else if(userIncome.getText().equals("N/A")){
                    message = "Select Income";
                    error = true;
                }

                if(error){
                    Toast.makeText(DemographicActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
                    Profile profile = new Profile(name, email, role, str_education, str_marital_status, str_living_status, str_income);
                    Intent intent = new Intent(DemographicActivity.this, ProfileActivity.class);
                    intent.putExtra(USER_PROFILE, profile);
                    startActivity(intent);
                }

            }
        });
    }
}