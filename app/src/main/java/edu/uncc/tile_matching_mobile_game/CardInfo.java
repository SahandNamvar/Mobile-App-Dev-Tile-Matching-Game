package edu.uncc.tile_matching_mobile_game;

import androidx.annotation.NonNull;

public class CardInfo {
    private int imageViewID;
    private int drawableID;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    // Constructor
    public CardInfo(int imageViewID, int drawableID) {
        this.imageViewID = imageViewID;
        this.drawableID = drawableID;
    }

    // Getter and Setters
    public int getImageViewID() {
        return imageViewID;
    }

    public void setImageViewID(int imageViewID) {
        this.imageViewID = imageViewID;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    // To String

    @NonNull
    @Override
    public String toString() {
        return "CardInfo{" +
                "imageViewID=" + imageViewID +
                ", drawableID=" + drawableID +
                ", isFlipped=" + isFlipped +
                ", isMatched=" + isMatched +
                '}';
    }
}
