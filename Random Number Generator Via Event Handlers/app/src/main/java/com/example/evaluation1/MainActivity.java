package com.example.evaluation1;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView generatedNum;
    TextView pickingNum;
    ImageView dice1;
    ImageView dice2;
    SeekBar seekbarNum;
    Integer maxNum;
    Integer minNum;
    Integer randomNum;
    private int[] diceArray = new int[] {R.drawable.ic_dice_1, R.drawable.ic_dice_2, R.drawable.ic_dice_3, R.drawable.ic_dice_4, R.drawable.ic_dice_5, R.drawable.ic_dice_6};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        generatedNum = findViewById(R.id.textViewGeneratedNumber);
        pickingNum = findViewById(R.id.textViewPickingNumber);
        dice1 = findViewById(R.id.imageViewDice1);
        dice2 = findViewById(R.id.imageViewDice2);
        seekbarNum = findViewById(R.id.seekBarNumber);
        dice1.setVisibility(View.INVISIBLE);
        dice2.setVisibility(View.INVISIBLE);
        generatedNum.setText("");

        minNum = 1;
        maxNum = 7;
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dice1.setVisibility(View.INVISIBLE);
                dice2.setVisibility(View.INVISIBLE);
                generatedNum.setText("");
                seekbarNum.setProgress(1);
                pickingNum.setText(String.valueOf("Picking from 1 to 7"));
                minNum = 1;
                maxNum = 7;
            }
        });
        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dice1.setVisibility(View.INVISIBLE);
                dice2.setVisibility(View.INVISIBLE);
                //
                //Formula Available from W3docs
                //https://www.w3docs.com/snippets/java/how-can-i-generate-random-number-in-specific-range-in-android.html
                randomNum = new Random().nextInt((maxNum - minNum)+1) + minNum;
                //
                generatedNum.setText(String.valueOf("Generated: " + randomNum));
                if (randomNum<=6){
                    dice1.setVisibility(View.VISIBLE);
                    dice1.setImageResource(diceArray[randomNum-1]);
                }
                if (randomNum>6){
                    dice1.setVisibility(View.VISIBLE);
                    dice2.setVisibility(View.VISIBLE);
                    dice1.setImageResource(diceArray[5]);
                    dice2.setImageResource(diceArray[randomNum-7]);
                }

            }
        });
        seekbarNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress + 6;
                maxNum = progress;
                pickingNum.setText(String.valueOf("Picking from 1 to " + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}