package edu.uncc.assignment3;

import static edu.uncc.assignment3.DemographicActivity.USER_PROFILE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView textUserName, textUserEmail, textUserRole, textUserIncome, textUserEducation, textUserMaritalStatus, textUserLivingStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textUserName = findViewById(R.id.textUserName);
        textUserEmail = findViewById(R.id.textUserEmail);
        textUserRole = findViewById(R.id.textUserRole);
        textUserIncome = findViewById(R.id.textUserIncome);
        textUserEducation = findViewById(R.id.textUserEducation);
        textUserMaritalStatus = findViewById(R.id.textUserMaritalStatus);
        textUserLivingStatus = findViewById(R.id.textUserLivingStatus);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_PROFILE)) {
            Profile profile = (Profile) intent.getSerializableExtra(USER_PROFILE);

            textUserName.setText(profile.getName());
            textUserEmail.setText(profile.getEmail());
            String str_role = "";
            if(profile.getRole() == R.id.radioStudent){
                str_role = getString(R.string.idf_role_student);
            } else if(profile.getRole() == R.id.radioEmployee){
                str_role = getString(R.string.idf_role_employee);
            } else if(profile.getRole() == R.id.radioOther){
                str_role = getString(R.string.idf_role_other);
            }
            textUserRole.setText(str_role);
            textUserIncome.setText(profile.getIncome());
            textUserEducation.setText(profile.getEducation());
            textUserMaritalStatus.setText(profile.getMaritalStatus());
            textUserLivingStatus.setText(profile.getLivingStatus());
        }

    }
}