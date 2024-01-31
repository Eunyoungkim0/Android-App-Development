package edu.uncc.assignment3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IdentificationActivity extends AppCompatActivity {

    EditText editName, editEmail;
    RadioGroup radioRole;
    RadioButton radioStudent, radioEmployee, radioOther;
    public static final String USER_IDENTIFICATION = "IDENTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        radioRole = findViewById(R.id.radioRole);
        radioStudent = findViewById(R.id.radioStudent);
        radioEmployee = findViewById(R.id.radioEmployee);
        radioOther = findViewById(R.id.radioOther);

        findViewById(R.id.btnIdfNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                int role = radioRole.getCheckedRadioButtonId();
                String str_role = "";
                if(role == R.id.radioStudent){
                    str_role = getString(R.string.idf_role_student);
                } else if(role == R.id.radioEmployee){
                    str_role = getString(R.string.idf_role_employee);
                } else if(role == R.id.radioOther){
                    str_role = getString(R.string.idf_role_other);
                }

                if(name.isEmpty()){
                    Toast.makeText(IdentificationActivity.this, "Enter name!!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(IdentificationActivity.this, "Enter email!!", Toast.LENGTH_SHORT).show();
                } else if(str_role.isEmpty()){
                    Toast.makeText(IdentificationActivity.this, "Select Role!!", Toast.LENGTH_SHORT).show();
                } else {
                    Identification identification = new Identification(name, email, role);
                    Intent intent = new Intent(IdentificationActivity.this, DemographicActivity.class);
                    intent.putExtra(USER_IDENTIFICATION, identification);
                    startActivity(intent);
                }
            }
        });
    }
}