package com.example.david.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int NUMBER_OF_GENRES = 5;
    final int NUMBER_OF_AUTHORS = 6;
    final int NUMBER_OF_ALBUMS = 7;
    final int NUMBER_OF_SONGS = 99;
    final int NUMBER_OF_PLAYLISTS = 2;
    final int NUMBER_OF_PLAYLISTS_SONGS = 38;

    ArrayList<Album> albums;
    ArrayList<Author> authors;
    ArrayList<MusicGenre> musicGenres;
    ArrayList<Playlist> playlists;
    ArrayList<PlaylistSong> playlistSongs;
    ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Create the global structures and fill them with data.
        getData();

        // Find the View that shows the category
        TextView artists = (TextView) findViewById(R.id.artists);
        TextView playlists = (TextView) findViewById(R.id.playlists);
        TextView albums = (TextView) findViewById(R.id.albums);
        TextView songs = (TextView) findViewById(R.id.songs);
        TextView genres = (TextView) findViewById(R.id.genres);

        // Set a click listener on every View
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the artists View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(artistsIntent);
            }
        });
        playlists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the playlists View is clicked on.
            @Override
            public void onClick(View view) {
                Intent playlistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(playlistsIntent);
            }
        });
        albums.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the albums View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(albumsIntent);
            }
        });
        genres.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the genres View is clicked on.
            @Override
            public void onClick(View view) {
                Intent genresIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(genresIntent);
            }
        });
        songs.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the songs View is clicked on.
            @Override
            public void onClick(View view) {
                Intent songsIntent = new Intent(MainActivity.this, RowListActivity.class);
                startActivity(songsIntent);
            }
        });
    }

    /**
     * Get global data for songs, authors, playlists, etc.
     */
    void getData() {
        int i, genreId, authorId, albumId, albumAuthorId, songId, songAlbumId, songGenreId;
        int playlistId;
        String genreName, genreImage, authorName, authorImage, albumName, albumImage, songName;
        String playlistName, playlistImage;
        MusicGenre musicGenre;
        Author author;
        Album album;
        Song song;
        Playlist playlist;
        PlaylistSong playlistSong;

        // Read music genres.
        musicGenres = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_GENRES; i++) {
            genreId = Integer.parseInt(getResources().getString(getResources().getIdentifier("genre_" + i + "_id", "string", getPackageName())));
            genreName = getResources().getString(getResources().getIdentifier("genre_" + i + "_name", "string", getPackageName()));
            genreImage = getResources().getString(getResources().getIdentifier("genre_" + i + "_image", "string", getPackageName()));

            musicGenre = new MusicGenre(genreId, genreName, genreImage);
            musicGenres.add(musicGenre);
        }

        // Read authors.
        authors = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_AUTHORS; i++) {
            authorId = Integer.parseInt(getResources().getString(getResources().getIdentifier("author_" + i + "_id", "string", getPackageName())));
            authorName = getResources().getString(getResources().getIdentifier("author_" + i + "_name", "string", getPackageName()));
            authorImage = getResources().getString(getResources().getIdentifier("author_" + i + "_image", "string", getPackageName()));

            author = new Author(authorId, authorName, authorImage);
            authors.add(author);
        }

        // Read albums.
        albums = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_ALBUMS; i++) {
            albumId = Integer.parseInt(getResources().getString(getResources().getIdentifier("album_" + i + "_id", "string", getPackageName())));
            albumName = getResources().getString(getResources().getIdentifier("album_" + i + "_name", "string", getPackageName()));
            albumImage = getResources().getString(getResources().getIdentifier("album_" + i + "_image", "string", getPackageName()));
            albumAuthorId = Integer.parseInt(getResources().getString(getResources().getIdentifier("album_" + i + "_author_id", "string", getPackageName())));

            album = new Album(albumId, albumName, albumImage, albumAuthorId);
            albums.add(album);
        }

        // Read songs.
        songs = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_SONGS; i++) {
            songId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_id", "string", getPackageName())));
            songName = getResources().getString(getResources().getIdentifier("song_" + i + "_name", "string", getPackageName()));
            songAlbumId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_album_id", "string", getPackageName())));
            songGenreId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_genre_id", "string", getPackageName())));

            song = new Song(songId, songName, songAlbumId, songGenreId);
            songs.add(song);
        }

        // Read playlists.
        playlists = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_PLAYLISTS; i++) {
            playlistId = Integer.parseInt(getResources().getString(getResources().getIdentifier("playlist_" + i + "_id", "string", getPackageName())));
            playlistName = getResources().getString(getResources().getIdentifier("playlist_" + i + "_name", "string", getPackageName()));
            playlistImage = getResources().getString(getResources().getIdentifier("playlist_" + i + "_image", "string", getPackageName()));

            playlist = new Playlist(playlistId, playlistName, playlistImage);
            playlists.add(playlist);
        }

        // Read songs per playlist.
        playlistSongs = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_PLAYLISTS_SONGS; i++) {
            playlistId = Integer.parseInt(getResources().getString(getResources().getIdentifier("sp_playlist_" + i + "_id", "string", getPackageName())));
            songId = Integer.parseInt(getResources().getString(getResources().getIdentifier("sp_song_" + i + "_id", "string", getPackageName())));

            playlistSong = new PlaylistSong(playlistId, songId);
            playlistSongs.add(playlistSong);
        }
    }
}
