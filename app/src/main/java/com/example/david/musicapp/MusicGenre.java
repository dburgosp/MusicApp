package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class MusicGenre {
    private int genreId;
    private String genreName;
    private String genreImage;

    public MusicGenre(int genreId, String genreName, String genreImage) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreImage = genreImage;
    }

    public int getGenreId() {
        return this.genreId;
    }

    public String getGenreName() {
        return this.genreName;
    }

    public String getGenreImage() { return this.genreImage; }
}
