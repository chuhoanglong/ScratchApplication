package com.example.scratchapplication.model;

public class Save {
    private String uId;
    private String rId;

    public Save(String uId, String rId) {
        this.uId = uId;
        this.rId = rId;
    }

    public String getuId() {
        return uId;
    }

    public String getrId() {
        return rId;
    }
}
