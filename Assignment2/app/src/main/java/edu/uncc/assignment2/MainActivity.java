package edu.uncc.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewGameStatus;
    private final int[] imageViewRewards = {R.id.image1, R.id.image10, R.id.image50, R.id.image100, R.id.image300, R.id.image1000, R.id.image10000, R.id.image50000, R.id.image100000, R.id.image500000};
    private final int[] drawableRewards = {R.drawable.reward_open_1, R.drawable.reward_open_10, R.drawable.reward_open_50, R.drawable.reward_open_100, R.drawable.reward_open_300, R.drawable.reward_open_1000, R.drawable.reward_open_10000, R.drawable.reward_open_50000, R.drawable.reward_open_100000, R.drawable.reward_open_500000};
    private final int[] imageViewSuitcases = {R.id.suitcase_1, R.id.suitcase_2, R.id.suitcase_3, R.id.suitcase_4, R.id.suitcase_5, R.id.suitcase_6, R.id.suitcase_7, R.id.suitcase_8, R.id.suitcase_9, R.id.suitcase_10};
    private final int[] drawableSuitcases = {R.drawable.suitcase_open_1, R.drawable.suitcase_open_10, R.drawable.suitcase_open_50, R.drawable.suitcase_open_100, R.drawable.suitcase_open_300, R.drawable.suitcase_open_1000, R.drawable.suitcase_open_10000, R.drawable.suitcase_open_50000, R.drawable.suitcase_open_100000, R.drawable.suitcase_open_500000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGameStatus = findViewById(R.id.textViewGameStatus);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setupNewGame();
            }
        });
        setupNewGame();
    }

    private void setupNewGame(){
        ArrayList<Integer> shuffledDrawableIds = new ArrayList<>();
        textViewGameStatus.setText("Choose 4 Cases");

        for(int drawableId: drawableSuitcases){
            shuffledDrawableIds.add(drawableId);
        }

        Collections.sort(shuffledDrawableIds);

        for(int i=0; i < shuffledDrawableIds.size(); i++){
            int imageViewId = imageViewSuitcases[i];
            int drawableId = shuffledDrawableIds.get(i);
            SuitcaseInfo suitcaseInfo = new SuitcaseInfo(imageViewId, drawableId);
            ImageView imageView = findViewById(imageViewId);
            imageView.setTag(suitcaseInfo);
            imageView.setOnClickListener(this);
        }

        suitcaseInfo1 = null;
    }

    SuitcaseInfo suitcaseInfo1 = null;

    @Override
    public void onClick(View v) {
        ImageView imageView = (ImageView) v;
        SuitcaseInfo suitcaseInfo = (SuitcaseInfo) imageView.getTag();
        Log.d("demo", "onClick: " + suitcaseInfo);

        if(!suitcaseInfo.isFlipped()){
            imageView.setImageResource(suitcaseInfo.getDrawableId());
            suitcaseInfo.setFlipped(true);

            if(suitcaseInfo1 == null){
                suitcaseInfo1 = suitcaseInfo;
            }else{

            }
        }
    }
}