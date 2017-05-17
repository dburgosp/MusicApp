package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class Album implements java.io.Serializable {
    private int albumId;
    private String albumName;
    private String albumImage;
    private int albumAuthorId;

    public Album(int albumId, String albumName, String albumImage, int albumAuthorId) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumImage = albumImage;
        this.albumAuthorId = albumAuthorId;
    }

    public int getAlbumId() { return this.albumId; }

    public String getAlbumName() { return this.albumName;  }

    public String getAlbumImage() { return this.albumImage; }

    public int getAlbumAuthorId() { return this.albumAuthorId; }
}
