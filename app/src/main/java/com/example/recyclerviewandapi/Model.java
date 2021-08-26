package com.example.recyclerviewandapi;

public class Model {
    private String mTitle, mDesc, mImage, mGenre;

    public Model(String mTitle, String mDesc, String mGenre, String mImage) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mGenre = mGenre;
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmGenre() {
        return mGenre;
    }

    public String getmImage() {
        return mImage;
    }
}
