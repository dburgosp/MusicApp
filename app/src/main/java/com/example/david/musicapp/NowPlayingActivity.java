package com.example.david.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {
    // Global variables.
    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    TextView nowPlayingTitle, nowPlayingSubtitle;
    ImageView nowPlayingBackground, nowPlayingImage, nowPlayingButton, prevSongButton;
    ImageView nextSongButton, likeButton, dislikeButton;

    // Shared data with other activities.
    int param_type, param_artist, param_album, param_genre, param_playlist, param_now_playing_song = -1;
    boolean param_now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        // Get views from the current layout.
        nowPlayingBackground = (ImageView) findViewById(R.id.now_playing_background);
        nowPlayingImage = (ImageView) findViewById(R.id.now_playing_image);
        nowPlayingTitle = (TextView) findViewById(R.id.now_playing_title);
        nowPlayingSubtitle = (TextView) findViewById(R.id.now_playing_subtitle);
        nowPlayingButton = (ImageView) findViewById(R.id.now_playing_button);
        prevSongButton = (ImageView) findViewById(R.id.now_playing_prev);
        nextSongButton = (ImageView) findViewById(R.id.now_playing_next);
        likeButton = (ImageView) findViewById(R.id.now_playing_like);
        dislikeButton = (ImageView) findViewById(R.id.now_playing_dislike);

        // Get music data and parameters.
        getData(getIntent());

        // Define behaviour of "play/stop" button.
        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                if (param_now_playing) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.pause_music, Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_play_circle_filled_white_48dp));
                    param_now_playing = false;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.play_music, Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_circle_filled_white_48dp));
                    param_now_playing = true;
                }
            }
        });

        // Behaviour of "skip previous song" button.
        prevSongButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.previous_song, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Behaviour of "skip next song" button.
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.next_song, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Behaviour of "like" button.
        likeButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.like_song, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Behaviour of "dislike" button.
        dislikeButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.dislike_song, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("param_now_playing_song", param_now_playing_song);
        outState.putBoolean("param_now_playing", param_now_playing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        param_now_playing_song = savedInstanceState.getInt("param_now_playing_song");
        param_now_playing = savedInstanceState.getBoolean("param_now_playing");

        // Hide/show "now playing" section.
        setNowPlayingView();
    }

    /**
     * Captures the "back" button of the app, in order to send parameters to the previous activity.
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Back to parent activity with extra data.
            Intent returnIntent = new Intent();
            returnIntent.putExtra("param_type", param_type);
            returnIntent.putExtra("param_artist", param_artist);
            returnIntent.putExtra("param_album", param_album);
            returnIntent.putExtra("param_genre", param_genre);
            returnIntent.putExtra("param_playlist", param_playlist);
            returnIntent.putExtra("param_now_playing_song", param_now_playing_song);
            returnIntent.putExtra("param_now_playing", param_now_playing);
            returnIntent.putExtra("albumsArrayList", albumsArrayList);
            returnIntent.putExtra("authorsArrayList", authorsArrayList);
            returnIntent.putExtra("musicGenresArrayList", musicGenresArrayList);
            returnIntent.putExtra("playlistsArrayList", playlistsArrayList);
            returnIntent.putExtra("playlistSongsArrayList", playlistSongsArrayList);
            returnIntent.putExtra("songsArrayList", songsArrayList);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Gets extra data from previous activity and sets, if necessary, the "now playing" view at the
     * bottom of the screen.
     *
     * @param intent
     */
    void getData(Intent intent) {
        // Get data.
        albumsArrayList = (ArrayList<Album>) intent.getSerializableExtra("albumsArrayList");
        authorsArrayList = (ArrayList<Author>) intent.getSerializableExtra("authorsArrayList");
        musicGenresArrayList = (ArrayList<MusicGenre>) intent.getSerializableExtra("musicGenresArrayList");
        playlistsArrayList = (ArrayList<Playlist>) intent.getSerializableExtra("playlistsArrayList");
        playlistSongsArrayList = (ArrayList<PlaylistSong>) intent.getSerializableExtra("playlistSongsArrayList");
        songsArrayList = (ArrayList<Song>) intent.getSerializableExtra("songsArrayList");
        param_type = intent.getIntExtra("param_type", 1);
        param_artist = intent.getIntExtra("param_artist", 1);
        param_album = intent.getIntExtra("param_album", 1);
        param_genre = intent.getIntExtra("param_genre", 1);
        param_playlist = intent.getIntExtra("param_playlist", 1);
        param_now_playing_song = intent.getIntExtra("param_now_playing_song", -1);
        param_now_playing = intent.getBooleanExtra("param_now_playing", false);

        // Set "now playing" section.
        setNowPlayingView();
    }

    /**
     * Sets data for the "now playing" view.
     */
    void setNowPlayingView() {
        // Image.
        int albumId = songsArrayList.get(param_now_playing_song).getSongAlbumId() - 1;
        String imageName = albumsArrayList.get(albumId).getAlbumImage();
        int imageId = getResources().getIdentifier(imageName, "drawable", NowPlayingActivity.this.getPackageName());
        nowPlayingImage.setImageDrawable(getDrawable(imageId));
        nowPlayingBackground.setImageDrawable(getDrawable(imageId));

        // Title.
        nowPlayingTitle.setText(songsArrayList.get(param_now_playing_song).getSongName());

        // Subtitle.
        int authorId = albumsArrayList.get(albumId).getAlbumAuthorId() - 1;
        nowPlayingSubtitle.setText(authorsArrayList.get(authorId).getAuthorName());

        // Set "play/stop" button.
        if (param_now_playing)
            nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_circle_filled_white_48dp));
        else
            nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_play_circle_filled_white_48dp));
    }
}
