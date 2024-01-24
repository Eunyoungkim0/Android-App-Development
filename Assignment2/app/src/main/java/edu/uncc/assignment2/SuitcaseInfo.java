package edu.uncc.assignment2;

public class SuitcaseInfo {
    private int imageViewId;
    private int drawableId;
    private boolean isFlipped = false;

    public SuitcaseInfo(int imageViewId, int drawableId) {
        this.imageViewId = imageViewId;
        this.drawableId = drawableId;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
