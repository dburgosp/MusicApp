package com.example.david.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Constants.
    final int NUMBER_OF_GENRES = 5;
    final int NUMBER_OF_AUTHORS = 6;
    final int NUMBER_OF_ALBUMS = 7;
    final int NUMBER_OF_SONGS = 99;
    final int NUMBER_OF_PLAYLISTS = 2;
    final int NUMBER_OF_PLAYLISTS_SONGS = 38;
    final int MAIN_ACTIVITY = 1;

    // Global variables.
    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    Intent artistsIntent, playlistsIntent, albumsIntent, genresIntent, songsIntent;
    RelativeLayout nowPlayingView;
    ImageView nowPlayingImage;
    TextView nowPlayingTitle;
    TextView nowPlayingSubtitle;
    ImageView nowPlayingButton;

    // Shared data with other activities.
    int param_now_playing_song = -1;
    boolean param_now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views from the current layout.
        nowPlayingView = (RelativeLayout) findViewById(R.id.main_now_playing);
        nowPlayingImage = (ImageView) findViewById(R.id.main_now_playing_image);
        nowPlayingTitle = (TextView) findViewById(R.id.main_now_playing_title);
        nowPlayingSubtitle = (TextView) findViewById(R.id.main_now_playing_subtitle);
        nowPlayingButton = (ImageView) findViewById(R.id.main_now_playing_button);

        // Create the global structures and fill them with data.
        setData();

        // Hide/show "now playing" section.
        setNowPlayingView();

        // Find the View that shows the category
        TextView artistsTextView = (TextView) findViewById(R.id.main_artists_textview);
        TextView playlistsTextView = (TextView) findViewById(R.id.main_playlists_textview);
        TextView albumsTextView = (TextView) findViewById(R.id.main_albums_textview);
        TextView songsTextView = (TextView) findViewById(R.id.main_songs_textview);
        TextView genresTextView = (TextView) findViewById(R.id.main_genre_textview);

        // Set a click listener on every View
        artistsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the artistsTextView View is clicked on.
            @Override
            public void onClick(View view) {
                artistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                artistsIntent.putExtra("param_type", 2); // List of artists.
                putExtraMusicData(artistsIntent);
                startActivityForResult(artistsIntent, MAIN_ACTIVITY);
            }
        });
        playlistsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the playlistsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                playlistsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                playlistsIntent.putExtra("param_type", 5); // List of playlists.
                putExtraMusicData(playlistsIntent);
                startActivityForResult(playlistsIntent, MAIN_ACTIVITY);
            }
        });
        albumsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the albumsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                albumsIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                albumsIntent.putExtra("param_type", 3); // List of albums.
                putExtraMusicData(albumsIntent);
                startActivityForResult(albumsIntent, MAIN_ACTIVITY);
            }
        });
        genresTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the genresTextView View is clicked on.
            @Override
            public void onClick(View view) {
                genresIntent = new Intent(MainActivity.this, ColumnListActivity.class);
                genresIntent.putExtra("param_type", 4); // List of available music genres.
                putExtraMusicData(genresIntent);
                startActivityForResult(genresIntent, MAIN_ACTIVITY);
            }
        });
        songsTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the songsArrayList View is clicked on.
            @Override
            public void onClick(View view) {
                songsIntent = new Intent(MainActivity.this, RowListActivity.class);
                songsIntent.putExtra("param_type", 1); // Assorted list of songs.
                putExtraMusicData(songsIntent);
                startActivityForResult(songsIntent, MAIN_ACTIVITY);
            }
        });
    }

    /**
     * Get global data for songsArrayList, authorsArrayList, playlistsArrayList, etc.
     */
    void setData() {
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
     *
     * @param intent
     */
    void putExtraMusicData(Intent intent) {
        intent.putExtra("albumsArrayList", albumsArrayList);
        intent.putExtra("authorsArrayList", authorsArrayList);
        intent.putExtra("musicGenresArrayList", musicGenresArrayList);
        intent.putExtra("playlistsArrayList", playlistsArrayList);
        intent.putExtra("playlistSongsArrayList", playlistSongsArrayList);
        intent.putExtra("songsArrayList", songsArrayList);

        intent.putExtra("param_now_playing_song", param_now_playing_song);
        intent.putExtra("param_now_playing", param_now_playing);
    }

    /**
     * Hides or shows the "now playing" view.
     */
    void setNowPlayingView() {
        if (this.param_now_playing_song < 0) {
            // Hide "now playing" view.
            nowPlayingView.setVisibility(View.GONE);
        } else {
            // Show and configure "now playing" view.

            // Image.
            int albumId = songsArrayList.get(param_now_playing_song).getSongAlbumId() - 1;
            String imageName = albumsArrayList.get(albumId).getAlbumImage();
            int imageId = getResources().getIdentifier(imageName, "drawable", MainActivity.this.getPackageName());
            nowPlayingImage.setImageDrawable(getDrawable(imageId));

            // Title.
            nowPlayingTitle.setText(songsArrayList.get(param_now_playing_song).getSongName());

            // Subtitle.
            int authorId = albumsArrayList.get(albumId).getAlbumAuthorId() - 1;
            nowPlayingSubtitle.setText(authorsArrayList.get(authorId).getAuthorName());

            // Set visibility and "play/stop" button.
            if (param_now_playing)
                nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_black_36dp));
            else
                nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_black_36dp));
            nowPlayingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get data from previous activity.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                // Get data.
                albumsArrayList = (ArrayList<Album>) data.getSerializableExtra("albumsArrayList");
                authorsArrayList = (ArrayList<Author>) data.getSerializableExtra("authorsArrayList");
                musicGenresArrayList = (ArrayList<MusicGenre>) data.getSerializableExtra("musicGenresArrayList");
                playlistsArrayList = (ArrayList<Playlist>) data.getSerializableExtra("playlistsArrayList");
                playlistSongsArrayList = (ArrayList<PlaylistSong>) data.getSerializableExtra("playlistSongsArrayList");
                songsArrayList = (ArrayList<Song>) data.getSerializableExtra("songsArrayList");
                param_now_playing_song = data.getIntExtra("param_now_playing_song", -1);
                param_now_playing = data.getBooleanExtra("param_now_playing", false);

                // Hide/show "now playing" section.
                setNowPlayingView();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public int getNowPlaying() {
        return param_now_playing_song;
    }
}
