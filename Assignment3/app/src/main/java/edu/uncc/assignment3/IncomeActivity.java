package edu.uncc.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class IncomeActivity extends AppCompatActivity {

    SeekBar seekBarIncome;
    TextView textIncomeStatus;
    static public final String USER_INCOME = "INCOME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        seekBarIncome = findViewById(R.id.seekBarIncome);
        textIncomeStatus = findViewById(R.id.textIncomeStatus);
        seekBarIncome.setProgress(2);
        textIncomeStatus.setText("$50K to <$100K");

        seekBarIncome.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String str_progress = "";

                if(progress == 0){
                    str_progress = "<$25K";
                }else if(progress == 1){
                    str_progress = "$25K to <$50K";
                }else if(progress == 2){
                    str_progress = "$50K to <$100K";
                }else if(progress == 3){
                    str_progress = "$100K to <$200K";
                }else if(progress == 4){
                    str_progress = ">$200K";
                }

                textIncomeStatus.setText(str_progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btnHICancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.btnHISubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(USER_INCOME, textIncomeStatus.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}