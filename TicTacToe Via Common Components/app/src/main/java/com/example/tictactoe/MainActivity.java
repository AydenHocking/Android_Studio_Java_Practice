package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
/*
Ayden Hocking
Assignment 2
MainActivity.java
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewGameStatus;
    private final int[] imageViewCardIds = {R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8};
    private final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private String currentPlayer = "Player One";
    private boolean gameOver = false;
    int turnNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewGameStatus = findViewById(R.id.textViewGameStatus);

        findViewById(R.id.buttonReset1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNewGame();
            }
        });
        setupNewGame();
    }

    private void setupNewGame() {
        for (int imageViewCardId : imageViewCardIds) {
            ImageView imageView = findViewById(imageViewCardId);
            animateTransition(imageView, R.drawable.squareimage, 1f);
            imageView.setTag("square");
            imageView.setOnClickListener(this);
        }
        currentPlayer = "Player One";
        textViewGameStatus.setText("Player One's Turn");
        textViewGameStatus.setTextColor(0xFF3292BE);

        gameOver = false;
        turnNum = 0;

    }

    @Override
    public void onClick(View v) {
        if (gameOver) return;
        ImageView imageViewSelection = (ImageView) v;
        String selectionStatus = (String) imageViewSelection.getTag();
        if (selectionStatus.equals("square")) {
            if (currentPlayer.equals("Player One")) {
                animateTransition(imageViewSelection, R.drawable.ximage, 3f);
                imageViewSelection.setTag("x");
                textViewGameStatus.setText("Player Two's Turn");
                currentPlayer = "Player Two";
            } else {
                animateTransition(imageViewSelection, R.drawable.oimage, 3f);
                imageViewSelection.setTag("o");
                textViewGameStatus.setText("Player One's Turn");
                currentPlayer = "Player One";
            }
            turnNum ++;
        }
        if (turnNum <=9){
            checkWin();
        }else{
            textViewGameStatus.setText("No Player Wins");
            gameOver = true;
        }
    }

    private void checkWin() {
        for (int[] pos : winningPositions) {
            if (!findViewById(imageViewCardIds[pos[0]]).getTag().equals("square") && findViewById(imageViewCardIds[pos[0]]).getTag().equals(findViewById(imageViewCardIds[pos[1]]).getTag()) && findViewById(imageViewCardIds[pos[1]]).getTag().equals(findViewById(imageViewCardIds[pos[2]]).getTag())) {
                if(findViewById(imageViewCardIds[pos[0]]).getTag().equals("x")){
                    textViewGameStatus.setText("Player One Wins");
                    textViewGameStatus.setTextColor(0xFF32CD32);
                    animateTransition(findViewById(imageViewCardIds[pos[0]]),R.drawable.ximagewin, 3f );
                    animateTransition(findViewById(imageViewCardIds[pos[1]]),R.drawable.ximagewin, 3f );
                    animateTransition(findViewById(imageViewCardIds[pos[2]]),R.drawable.ximagewin, 3f );
                }else{
                    textViewGameStatus.setText("Player Two Wins");
                    textViewGameStatus.setTextColor(0xFF32CD32);
                    animateTransition(findViewById(imageViewCardIds[pos[0]]),R.drawable.oimagewin, 3f );
                    animateTransition(findViewById(imageViewCardIds[pos[1]]),R.drawable.oimagewin, 3f );
                    animateTransition(findViewById(imageViewCardIds[pos[2]]),R.drawable.oimagewin, 3f );
                }
                gameOver = true;
                return;
            }
        }

        for (int id : imageViewCardIds) {
            if (((String) findViewById(id).getTag()).equals("square")) {
                break;
            }
        }
        if (!gameOver && turnNum==9) {
            textViewGameStatus.setText("No Player Wins");
            gameOver = true;
        }
    }
    /*
       Animation Reference Source:
       https://codinginfinite.com/basic-android-animation-example/
       https://developer.android.com/reference/android/view/ViewPropertyAnimator
       Accessed: 9/4/2025
     */
    private void animateTransition(ImageView imageView, int newImageView, float scale) {
        imageView.animate()
                .scaleX(0f).scaleY(0f)
                .setDuration(50)
                .withEndAction(() -> {
                    imageView.setImageResource(newImageView);

                    imageView.animate()
                            .scaleX(scale).scaleY(scale)
                            .setDuration(50)
                            .start();
                })
                .start();
    }

}
