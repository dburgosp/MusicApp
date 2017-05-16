package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class Song {
    private int songId;
    private String songName;
    private int songAlbumId;
    private int songGenreId;

    public Song(int songId, String songName, int songAlbumId, int songGenreId) {
        this.songId = songId;
        this.songName = songName;
        this.songAlbumId = songAlbumId;
        this.songGenreId = songGenreId;
    }

    public int getSongId() { return this.songId; }

    public String getSongName() { return this.songName;  }

    public int getSongAlbumId() { return this.songAlbumId; }

    public int getSongGenreId() { return this.songGenreId; }
}
