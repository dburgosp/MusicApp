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

    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;

    Intent artistsIntent, playlistsIntent, albumsIntent, genresIntent, songsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Create the global structures and fill them with data.
        getData();

        // Find the View that shows the category
        TextView artistsTextView = (TextView) findViewById(R.id.artists);
        TextView playlistsTextView = (TextView) findViewById(R.id.playlists);
        TextView albumsTextView = (TextView) findViewById(R.id.albums);
        TextView songsTextView = (TextView) findViewById(R.id.songs);
        TextView genresTextView = (TextView) findViewById(R.id.genres);

        // Set a click listener on every View
        artistsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the artistsTextView View is clicked on.
            @Override
            public void onClick(View view) {
                artistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(artistsIntent);
            }
        });
        playlistsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the playlistsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                playlistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(playlistsIntent);
            }
        });
        albumsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the albumsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                albumsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                albumsIntent.putExtra("type", 6); // List of albums.
                putExtraMusicData(albumsIntent);
                startActivity(albumsIntent);
            }
        });
        genresTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the genresTextView View is clicked on.
            @Override
            public void onClick(View view) {
                genresIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                startActivity(genresIntent);
            }
        });
        songsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the songsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                songsIntent = new Intent(MainActivity.this, RowListActivity.class);
                songsIntent.putExtra("type", 1); // Assorted list of songs.
                putExtraMusicData(songsIntent);
                startActivity(songsIntent);
            }
        });
    }

    /**
     * Get global data for songsArrayList, authorsArrayList, playlistsArrayList, etc.
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
        musicGenresArrayList = new ArrayList<>();
        for (i = 1; i <= NUMBER_OF_GENRES; i++) {
            genreId = Integer.parseInt(getResources().getString(getResources().getIdentifier("genre_" + i + "_id", "string", getPackageName())));
            genreName = getResources().getString(getResources().getIdentifier("genre_" + i + "_name", "string", getPackageName()));
            genreImage = getResources().getString(getResources().getIdentifier("genre_" + i + "_image", "string", getPackageName()));
            musicGenre = new MusicGenre(genreId, genreName, genreImage);
            musicGenresArrayList.add(musicGenre);
        }

        // Read authorsArrayList.
        authorsArrayList = new ArrayList<>();
        for (i = 1; i <= NUMBER_OF_AUTHORS; i++) {
            authorId = Integer.parseInt(getResources().getString(getResources().getIdentifier("author_" + i + "_id", "string", getPackageName())));
            authorName = getResources().getString(getResources().getIdentifier("author_" + i + "_name", "string", getPackageName()));
            authorImage = getResources().getString(getResources().getIdentifier("author_" + i + "_image", "string", getPackageName()));
            author = new Author(authorId, authorName, authorImage);
            authorsArrayList.add(author);
        }

        // Read albumsArrayList.
        albumsArrayList = new ArrayList<>();
        for (i = 1; i <= NUMBER_OF_ALBUMS; i++) {
            albumId = Integer.parseInt(getResources().getString(getResources().getIdentifier("album_" + i + "_id", "string", getPackageName())));
            albumName = getResources().getString(getResources().getIdentifier("album_" + i + "_name", "string", getPackageName()));
            albumImage = getResources().getString(getResources().getIdentifier("album_" + i + "_image", "string", getPackageName()));
            albumAuthorId = Integer.parseInt(getResources().getString(getResources().getIdentifier("album_" + i + "_author_id", "string", getPackageName())));
            album = new Album(albumId, albumName, albumImage, albumAuthorId);
            albumsArrayList.add(album);
        }

        // Read songsArrayList.
        songsArrayList = new ArrayList<>();
        for (i = 1; i <= NUMBER_OF_SONGS; i++) {
            songId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_id", "string", getPackageName())));
            songName = getResources().getString(getResources().getIdentifier("song_" + i + "_name", "string", getPackageName()));
            songAlbumId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_album_id", "string", getPackageName())));
            songGenreId = Integer.parseInt(getResources().getString(getResources().getIdentifier("song_" + i + "_genre_id", "string", getPackageName())));
            song = new Song(songId, songName, songAlbumId, songGenreId);
            songsArrayList.add(song);
        }

        // Read playlistsArrayList.
        playlistsArrayList = new ArrayList<>();
        for (i = 1; i <= NUMBER_OF_PLAYLISTS; i++) {
            playlistId = Integer.parseInt(getResources().getString(getResources().getIdentifier("playlist_" + i + "_id", "string", getPackageName())));
            playlistName = getResources().getString(getResources().getIdentifier("playlist_" + i + "_name", "string", getPackageName()));
            playlistImage = getResources().getString(getResources().getIdentifier("playlist_" + i + "_image", "string", getPackageName()));
            playlist = new Playlist(playlistId, playlistName, playlistImage);
            playlistsArrayList.add(playlist);
        }

        // Read songsArrayList per playlist.
        playlistSongsArrayList = new ArrayList<>();
        for (i = 1; i < NUMBER_OF_PLAYLISTS_SONGS; i++) {
            playlistId = Integer.parseInt(getResources().getString(getResources().getIdentifier("sp_playlist_" + i + "_id", "string", getPackageName())));
            songId = Integer.parseInt(getResources().getString(getResources().getIdentifier("sp_song_" + i + "_id", "string", getPackageName())));
            playlistSong = new PlaylistSong(playlistId, songId);
            playlistSongsArrayList.add(playlistSong);
        }
    }

    /**
     * Share data with the next activity using intent.putExtra
     */
    void putExtraMusicData(Intent intent) {
        intent.putExtra("albumsArrayList", albumsArrayList);
        intent.putExtra("authorsArrayList", authorsArrayList);
        intent.putExtra("musicGenresArrayList", musicGenresArrayList);
        intent.putExtra("playlistsArrayList", playlistsArrayList);
        intent.putExtra("playlistSongsArrayList", playlistSongsArrayList);
        intent.putExtra("songsArrayList", songsArrayList);
    }
}
