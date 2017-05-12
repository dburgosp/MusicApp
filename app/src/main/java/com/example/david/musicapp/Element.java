package com.example.david.musicapp;

import android.graphics.drawable.Drawable;

/**
 * Created by David on 11/05/2017.
 */

public class Element {
    private String firstLine;
    private String secondLine;
    private Drawable image;

    // Constructor.
    public Element(String title, String subtitle, Drawable image) {
        this.firstLine = title;
        this.secondLine = subtitle;
        this.image = image;
    }

    // Setters.
    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }
    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }
    public void setImage(Drawable image) { this.image = image; }

    // Getters.
    public String getFirstLine() {
        return firstLine;
    }
    public String getSecondLine() {
        return secondLine;
    }
    public Drawable getImage() {
        return image;
    }
}
