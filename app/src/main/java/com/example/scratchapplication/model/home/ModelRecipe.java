package com.example.scratchapplication.model.home;

import com.example.scratchapplication.model.Comment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.Comparator;
import java.util.List;

public class ModelRecipe implements Comparator<ModelRecipe> {
    @SerializedName("rId")
    @Expose
    private String rId;
    @SerializedName("uId")
    @Expose
    private String uId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("urlCover")
    @Expose
    private String urlCover;
    @SerializedName("ingredients")
    @Expose
    private List<String> ingredients;
    @SerializedName("directions")
    @Expose
    private List<String> directions;
    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("usersLike")
    @Expose
    private List<String> usersLike;
    @SerializedName("profileAvatar")
    @Expose
    private String profileAvatar;
    @SerializedName("profileName")
    @Expose
    private String profileName;
    @SerializedName("filters")
    @Expose
    private List<String> filters;
    @SerializedName("dataComment")
    @Expose
    private List<Comment> dataComment;

    public ModelRecipe() {
    }

    public ModelRecipe(String uId, String name, String description, String urlCover,
                       List<String> ingredients, List<String> directions, int like, List<String> usersLike,
                       String profileAvatar, String profileName, List<String> filters) {
        this.uId = uId;
        this.name = name;
        this.description = description;
        this.urlCover = urlCover;
        this.ingredients = ingredients;
        this.directions = directions;
        this.like = like;
        this.usersLike = usersLike;
        this.profileAvatar = profileAvatar;
        this.profileName = profileName;
        this.filters = filters;
    }

    public List<Comment> getDataComment() {
        return dataComment;
    }

    public String getrId() {
        return rId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public List<String> getUsersLike() {
        return usersLike;
    }

    public void setUsersLike(List<String> usersLike) {
        this.usersLike = usersLike;
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

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filter) {
        this.filters = filter;
    }

    @Override
    public int compare(ModelRecipe o1, ModelRecipe o2) {
        return o1.getUsersLike().size() - o2.getUsersLike().size();
    }
}
