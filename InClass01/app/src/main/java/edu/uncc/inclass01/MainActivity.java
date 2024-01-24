// Eunyoung Kim
// ITIS 5180 - Assignment 1

package edu.uncc.inclass01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextItemPrice;
    TextView textViewSeekBar, textViewDiscount, textViewFinalPrice;
    SeekBar seekBar;
    RadioGroup radioGroup;
    RadioButton radioButton10, radioButton15, radioButton18, radioButtonCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextItemPrice = findViewById(R.id.editItemPrice);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton10 = findViewById(R.id.radioButton10);
        radioButton15 = findViewById(R.id.radioButton15);
        radioButton18 = findViewById(R.id.radioButton18);
        radioButtonCustom = findViewById(R.id.radioButtonCustom);
        textViewSeekBar = findViewById(R.id.textViewSeekBar);
        seekBar = findViewById(R.id.seekBar);
        textViewDiscount = findViewById(R.id.textViewDiscount);
        textViewFinalPrice = findViewById(R.id.textViewFinalPrice);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBar.setText(progress + "%");
                radioGroup.check(R.id.radioButtonCustom);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextItemPrice.setText("");
                radioGroup.check(R.id.radioButton10);
                seekBar.setProgress(25);
                textViewDiscount.setText("0.00");
                textViewFinalPrice.setText("0.00");
            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(editTextItemPrice.getText().toString().equals("")){
                    textViewDiscount.setText("0.00");
                    textViewFinalPrice.setText("0.00");
                    Toast.makeText(MainActivity.this, "Enter Item Price", Toast.LENGTH_SHORT).show();
                } else{
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    int itemPrice = Integer.parseInt(String.valueOf(editTextItemPrice.getText()));
                    double discount = 0.0;
                    double finalPrice;

                    if(checkedId == R.id.radioButton10){
                        discount = itemPrice * 0.1;
                    }else if(checkedId == R.id.radioButton15){
                        discount = itemPrice * 0.15;
                    }else if(checkedId == R.id.radioButton18){
                        discount = itemPrice * 0.18;
                    }else if(checkedId == R.id.radioButtonCustom){
                        discount = itemPrice * seekBar.getProgress() / 100;
                    }
                    finalPrice = itemPrice - discount;
                    textViewDiscount.setText(String.valueOf(discount));
                    textViewFinalPrice.setText(String.valueOf(finalPrice));
                }
            }
        });
    }
}