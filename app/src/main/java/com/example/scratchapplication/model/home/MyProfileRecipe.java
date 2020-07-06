package com.example.scratchapplication.obj;

public class MyProfileRecipe {
    private int imageRecipe;
    private String nameRecipe;


    public MyProfileRecipe( int imageRecipe, String nameRecipe) {

        this.imageRecipe = imageRecipe;
        this.nameRecipe = nameRecipe;

    }

    public int getImageRecipe() {
        return imageRecipe;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setImageRecipe(int imageRecipe) {
        this.imageRecipe = imageRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }
}