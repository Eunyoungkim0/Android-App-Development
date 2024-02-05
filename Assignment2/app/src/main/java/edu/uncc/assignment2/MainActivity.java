package edu.uncc.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewGameStatus;
    Button btnDeal, btnNoDeal;
    int totalAmount, caseCount, remainCount, round;
    private final int[] imageDrawableSuitcases = {R.drawable.suitcase_position_1, R.drawable.suitcase_position_2, R.drawable.suitcase_position_3, R.drawable.suitcase_position_4, R.drawable.suitcase_position_5, R.drawable.suitcase_position_6, R.drawable.suitcase_position_7, R.drawable.suitcase_position_8, R.drawable.suitcase_position_9, R.drawable.suitcase_position_10};
    private final int[] imageDrawableRewards = {R.drawable.reward_1, R.drawable.reward_10, R.drawable.reward_50, R.drawable.reward_100, R.drawable.reward_300, R.drawable.reward_1000, R.drawable.reward_10000, R.drawable.reward_50000, R.drawable.reward_100000, R.drawable.reward_500000};

    private final int[] imageViewRewards = {R.id.reward_1, R.id.reward_10, R.id.reward_50, R.id.reward_100, R.id.reward_300, R.id.reward_1000, R.id.reward_10000, R.id.reward_50000, R.id.reward_100000, R.id.reward_500000};
    private final int[] drawableRewards = {R.drawable.reward_open_1, R.drawable.reward_open_10, R.drawable.reward_open_50, R.drawable.reward_open_100, R.drawable.reward_open_300, R.drawable.reward_open_1000, R.drawable.reward_open_10000, R.drawable.reward_open_50000, R.drawable.reward_open_100000, R.drawable.reward_open_500000};
    private final int[] imageViewSuitcases = {R.id.suitcase_position_1, R.id.suitcase_position_2, R.id.suitcase_position_3, R.id.suitcase_position_4, R.id.suitcase_position_5, R.id.suitcase_position_6, R.id.suitcase_position_7, R.id.suitcase_position_8, R.id.suitcase_position_9, R.id.suitcase_position_10};
    private final int[] drawableSuitcases = {R.drawable.suitcase_open_1, R.drawable.suitcase_open_10, R.drawable.suitcase_open_50, R.drawable.suitcase_open_100, R.drawable.suitcase_open_300, R.drawable.suitcase_open_1000, R.drawable.suitcase_open_10000, R.drawable.suitcase_open_50000, R.drawable.suitcase_open_100000, R.drawable.suitcase_open_500000};

    private final int[] rewardAmount = {1, 10, 50, 100, 300, 1000, 10000, 50000, 100000, 500000};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGameStatus = findViewById(R.id.textViewGameStatus);
        btnDeal = findViewById(R.id.btnDeal);
        btnNoDeal = findViewById(R.id.btnNoDeal);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setupNewGame();
            }
        });

        findViewById(R.id.btnDeal).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnDeal.setVisibility(View.INVISIBLE);
                btnNoDeal.setVisibility(View.INVISIBLE);
                int bankDeal = (int) (totalAmount / remainCount * 0.6);
                DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
                decimalFormat.setMaximumFractionDigits(0);
                String formattedAmount = decimalFormat.format(bankDeal);
                textViewGameStatus.setText("You won " + formattedAmount);
            }
        });

        findViewById(R.id.btnNoDeal).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnDeal.setVisibility(View.INVISIBLE);
                btnNoDeal.setVisibility(View.INVISIBLE);

                round += 1;
                if(round == 3){
                    caseCount = 1;
                }else{
                    caseCount = 4;
                }
                textViewGameStatus.setText("Choose " + caseCount + " Cases");
            }
        });
        setupNewGame();
    }

    private void setupNewGame(){
        btnDeal.setVisibility(View.INVISIBLE);
        btnNoDeal.setVisibility(View.INVISIBLE);
        ArrayList<Integer> shuffledNumber = new ArrayList<>();
        for (int j=0; j<10; j++){
            shuffledNumber.add(j);
        }
        Collections.shuffle(shuffledNumber);

        for(int i=0; i < shuffledNumber.size(); i++){
            int imageViewId = imageViewSuitcases[i];
            int randomNum = shuffledNumber.get(i);

            int drawableId = drawableSuitcases[randomNum];
            int rewardId = imageViewRewards[i];
            int realRewardId = imageViewRewards[randomNum];
            int rewardDrawableId = drawableRewards[randomNum];
            int amount = rewardAmount[randomNum];

            SuitcaseInfo suitcaseInfo = new SuitcaseInfo(imageViewId, drawableId, realRewardId, rewardDrawableId, amount);
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(imageDrawableSuitcases[i]);
            imageView.setTag(suitcaseInfo);
            imageView.setOnClickListener(this);

            ImageView imageViewReward = findViewById(rewardId);
            imageViewReward.setImageResource(imageDrawableRewards[i]);
        }

        totalAmount = 0;
        remainCount = 10;
        for (int amount: rewardAmount){
            totalAmount += amount;
        }
        caseCount = 4;
        round = 1;
        textViewGameStatus.setText("Choose " + caseCount + " Cases");
    }


    @Override
    public void onClick(View v) {
        ImageView imageView = (ImageView) v;
        SuitcaseInfo suitcaseInfo = (SuitcaseInfo) imageView.getTag();
        boolean isFlipped = suitcaseInfo.isFlipped();

        if(caseCount != 0 && isFlipped == false){
            suitcaseInfo.setFlipped(true);
            imageView.setImageResource(suitcaseInfo.getDrawableId());
            ImageView imageViewReward = findViewById(suitcaseInfo.getRewardId());
            imageViewReward.setImageResource(suitcaseInfo.getRewardDrawableId());
            totalAmount -= suitcaseInfo.getAmount();
            remainCount -= 1;

            caseCount--;

            if(caseCount == 0){
                int bankDeal = (int) (totalAmount / remainCount * 0.6);
                DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
                decimalFormat.setMaximumFractionDigits(0);

                if(round == 3){
                    String formattedAmount = decimalFormat.format(totalAmount);
                    textViewGameStatus.setText("You won " + formattedAmount);
                    btnDeal.setVisibility(View.INVISIBLE);
                    btnNoDeal.setVisibility(View.INVISIBLE);
                }else{
                    String formattedAmount = decimalFormat.format(bankDeal);
                    textViewGameStatus.setText("Bank Deal is " + formattedAmount);
                    btnDeal.setVisibility(View.VISIBLE);
                    btnNoDeal.setVisibility(View.VISIBLE);
                }
            }else{
                textViewGameStatus.setText("Choose " + caseCount + " Cases");
            }
        }

    }
}