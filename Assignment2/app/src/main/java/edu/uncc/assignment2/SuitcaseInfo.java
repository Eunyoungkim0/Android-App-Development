package edu.uncc.assignment2;

public class SuitcaseInfo {
    private int imageViewId;
    private int drawableId;
    private int rewardId;
    private int rewardDrawableId;
    private int amount;
    private boolean isFlipped;


    public SuitcaseInfo(int imageViewId, int drawableId, int rewardId, int rewardDrawableId, int amount) {
        this.imageViewId = imageViewId;
        this.drawableId = drawableId;
        this.rewardId = rewardId;
        this.rewardDrawableId = rewardDrawableId;
        this.amount = amount;
        this.isFlipped = false;
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

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public int getRewardDrawableId() {
        return rewardDrawableId;
    }

    public void setRewardDrawableId(int rewardDrawableId) {
        this.rewardDrawableId = rewardDrawableId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
    @Override
    public String toString() {
        return "SuitcaseInfo{" +
                "imageViewId=" + imageViewId +
                ", drawableId=" + drawableId +
                ", rewardId=" + rewardId +
                ", rewardDrawableId=" + rewardDrawableId +
                ", amount=" + amount +
                '}';
    }
}
