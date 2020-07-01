package com.example.scratchapplication.obj;

public class RecipeFeed extends Recipe{
    private boolean isLiked;
    private boolean isSaved;

    public RecipeFeed() {
    }

    public RecipeFeed(boolean isLiked, boolean isSaved) {
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }

    public RecipeFeed(int profileAvatar, String profileName, int recipeCover, String recipeName, String recipeDescription, int likeCount, int cmtCount, boolean isLiked, boolean isSaved) {
        super(profileAvatar, profileName, recipeCover, recipeName, recipeDescription, likeCount, cmtCount);
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
