package com.example.scratchapplication.obj;

public class Recipe {
    private int profileAvatar;
    private String profileName;
    private int recipeCover;
    private String recipeName;
    private String recipeDescription;
    private int likeCount;
    private int cmtCount;
    private String servingTime;
    private String nutritionFacts;
    private String tags;


    public Recipe() {
    }

    public Recipe(int profileAvatar, String profileName, int recipeCover, String recipeName, String recipeDescription, int likeCount, int cmtCount) {
        this.profileAvatar = profileAvatar;
        this.profileName = profileName;
        this.recipeCover = recipeCover;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.likeCount = likeCount;
        this.cmtCount = cmtCount;
    }

    public int getProfileAvatar() {
        return profileAvatar;
    }

    public void setProfileAvatar(int profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getRecipeCover() {
        return recipeCover;
    }

    public void setRecipeCover(int recipeCover) {
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
}
