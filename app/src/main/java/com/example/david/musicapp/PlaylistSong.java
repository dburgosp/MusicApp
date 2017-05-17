package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class PlaylistSong implements java.io.Serializable {
    private int playlistId;
    private int songId;

    public PlaylistSong(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getPlaylistId() { return this.playlistId; }

    public int getSongId() { return this.songId; }
}
