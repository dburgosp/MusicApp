package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class Playlist implements java.io.Serializable {
    private int playlistId;
    private String playlistName;
    private String playlistImage;

    public Playlist(int playlistId, String playlistName, String playlistImage) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistImage = playlistImage;
    }

    public int getPlaylistId() { return this.playlistId; }

    public String getPlaylistName() { return this.playlistName; }

    public String getPlaylistImage() { return this.playlistImage; }
}
