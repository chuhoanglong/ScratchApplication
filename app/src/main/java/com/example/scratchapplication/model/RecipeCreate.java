package com.example.scratchapplication.model;

import java.util.List;

public class RecipeCreate {
    private String urlCover;
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
    private String servingTime;
    private String nutrition;
    private String tags;
    private String pId;

    public RecipeCreate() {
    }

    public RecipeCreate(String urlCover, String name, String description,
                        List<String> ingredients, List<String> directions,
                        String servingTime, String nutrition, String tags,
                        String pId) {
        this.urlCover = urlCover;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.servingTime = servingTime;
        this.nutrition = nutrition;
        this.tags = tags;
        this.pId = pId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getServingTime() {
        return servingTime;
    }

    public void setServingTime(String servingTime) {
        this.servingTime = servingTime;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
