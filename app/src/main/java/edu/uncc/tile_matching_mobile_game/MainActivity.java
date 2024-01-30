// Define the package and import necessary libraries
package edu.uncc.tile_matching_mobile_game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

// MainActivity class extends AppCompatActivity and implements View.OnClickListener interface
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare TextView and arrays for image view and drawable IDs
    TextView textViewGameStatus;
    private final int[] imageViewCardIds = {R.id.ImageViewCard0, R.id.ImageViewCard1, R.id.ImageViewCard2, R.id.ImageViewCard3, R.id.ImageViewCard4, R.id.ImageViewCard5, R.id.ImageViewCard6, R.id.ImageViewCard7, R.id.ImageViewCard8, R.id.ImageViewCard9, R.id.ImageViewCard10, R.id.ImageViewCard11, R.id.ImageViewCard12, R.id.ImageViewCard13, R.id.ImageViewCard14, R.id.ImageViewCard15};
    private final int[] drawableCardIds = {R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextView
        textViewGameStatus = findViewById(R.id.textViewGameStatus);

        // Set up a listener for the Reset button
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNewGame();
            }
        });

        // Call the setupNewGame method to initialize the game
        setupNewGame();
    }

    @SuppressLint("SetTextI18n")
    // Method to set up a new game
    private void setupNewGame(){
        // Create a list with shuffled drawable IDs for the cards
        ArrayList<Integer> shuffledDrawableIds = new ArrayList<>();
        for (int drawableId: drawableCardIds) {
            shuffledDrawableIds.add(drawableId);
            shuffledDrawableIds.add(drawableId);
        }
        Collections.shuffle(shuffledDrawableIds);

        // Loop through each card, set its image resource, tag, and click listener
        for (int i = 0; i < shuffledDrawableIds.size(); i++) {
            int imageViewId = imageViewCardIds[i];
            int drawableId = shuffledDrawableIds.get(i);
            CardInfo cardInfo = new CardInfo(imageViewId, drawableId);
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(R.drawable.covered_tile);
            imageView.setTag(cardInfo);
            imageView.setOnClickListener(this);
        }

        // Reset game variables and update game status text
        cardInfo1 = null;
        matchCount = 0;
        isWaiting = false;
        textViewGameStatus.setText("Match Count : " + matchCount);
    }

    // Declare variables for tracking card info, match count, and waiting status
    CardInfo cardInfo1 = null;
    int matchCount = 0;
    boolean isWaiting = false;

    // Method to cover a card by setting its image resource to a covered tile
    private void coverCard(CardInfo cardInfo){
        ImageView imageView = findViewById(cardInfo.getImageViewID());
        imageView.setImageResource(R.drawable.covered_tile);
        cardInfo.setFlipped(false);
        cardInfo.setMatched(false);
    }

    @SuppressLint("SetTextI18n")
    // Method triggered on card click
    @Override
    public void onClick(View v) {

        // Check if not waiting for a delay
        if(!isWaiting){
            ImageView imageView = (ImageView) v;
            CardInfo cardInfo = (CardInfo) imageView.getTag();
            Log.d("demo", "onClick: " + cardInfo);

            // Check if the card is not flipped and not already matched
            if(!cardInfo.isFlipped() && !cardInfo.isMatched()){
                imageView.setImageResource(cardInfo.getDrawableID());
                cardInfo.setFlipped(true);

                // Check if it's the first card of a pair
                if(cardInfo1 == null){
                    cardInfo1 = cardInfo;
                } else {
                    // Check if the second card matches the first card
                    if(cardInfo.getDrawableID() == cardInfo1.getDrawableID()){
                        // Match case
                        matchCount++;
                        cardInfo1.setMatched(true);
                        cardInfo.setMatched(true);

                        textViewGameStatus.setText("Match Count : " + matchCount);

                        // Check if all pairs are matched
                        if(matchCount == 8){
                            // Game completed
                            textViewGameStatus.setText("Game Completed!!");
                        }
                        cardInfo1 = null;
                    } else {
                        // No match case
                        isWaiting = true;
                        // Delay for 2 seconds before covering the cards again
                        imageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isWaiting = false;
                                coverCard(cardInfo);
                                coverCard(cardInfo1);
                                cardInfo1 = null;
                            }
                        }, 2000);
                    }
                }
            }
        }
    }
}
