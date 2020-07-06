package com.example.scratchapplication.model.home;

public class RecipeFeed {
    private String rId;
    private String profileAvatar;
    private String profileName;
    private String recipeCover;
    private String recipeName;
    private String recipeDescription;
    private int likeCount;
    private int cmtCount;
    private boolean isLiked;
    private boolean isSaved;

    public RecipeFeed() {
    }

    public RecipeFeed(String rId, String profileAvatar, String profileName, String recipeCover,
                      String recipeName, String recipeDescription, int likeCount, int cmtCount,
                      boolean isLiked, boolean isSaved) {
        this.rId = rId;
        this.profileAvatar = profileAvatar;
        this.profileName = profileName;
        this.recipeCover = recipeCover;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.likeCount = likeCount;
        this.cmtCount = cmtCount;
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getProfileAvatar() {
        return profileAvatar;
    }

    public void setProfileAvatar(String profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getRecipeCover() {
        return recipeCover;
    }

    public void setRecipeCover(String recipeCover) {
        this.recipeCover = recipeCover;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCmtCount() {
        return cmtCount;
    }

    public void setCmtCount(int cmtCount) {
        this.cmtCount = cmtCount;
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
