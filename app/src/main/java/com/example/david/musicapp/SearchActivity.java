package com.example.david.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    // Constants.
    final int SEARCH_ACTIVITY = 6;

    // Global variables.
    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    RelativeLayout nowPlayingView;
    ImageView nowPlayingImage, nowPlayingButton;
    TextView nowPlayingTitle, nowPlayingSubtitle;

    // Shared data with other activities.
    int param_type, param_artist, param_album, param_genre, param_playlist, param_now_playing_song = -1;
    boolean param_now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get views from the current layout.
        nowPlayingView = (RelativeLayout) findViewById(R.id.search_now_playing);
        nowPlayingImage = (ImageView) findViewById(R.id.search_now_playing_image);
        nowPlayingTitle = (TextView) findViewById(R.id.search_now_playing_title);
        nowPlayingSubtitle = (TextView) findViewById(R.id.search_now_playing_subtitle);
        nowPlayingButton = (ImageView) findViewById(R.id.search_now_playing_button);

        // Get music data and parameters.
        getData(getIntent());

        // Define behaviour of the "Now playing" section.
        nowPlayingView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when any element into the nowPlayingSection
            // RelativeLayout is clicked on.
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(SearchActivity.this, NowPlayingActivity.class);
                putExtraMusicData(nowPlayingIntent);
                startActivityForResult(nowPlayingIntent, SEARCH_ACTIVITY);
            }
        });

        // Define behaviour of play/stop button.
        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                if (param_now_playing) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.pause_music, Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_black_36dp));
                    param_now_playing = false;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.play_music, Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_black_36dp));
                    param_now_playing = true;
                }
            }
        });
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
            int imageId = getResources().getIdentifier(imageName, "drawable", SearchActivity.this.getPackageName());
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
     * Gets extra data from previous activity and sets, if necessary, the "now playing" view at the
     * bottom of the screen.
     *
     * @param intent from which extra data is taken.
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

        // Hide/show "now playing" section.
        setNowPlayingView();
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

        intent.putExtra("param_type", param_type);
        intent.putExtra("param_artist", param_artist);
        intent.putExtra("param_album", param_album);
        intent.putExtra("param_genre", param_genre);
        intent.putExtra("param_playlist", param_playlist);
        intent.putExtra("param_now_playing_song", param_now_playing_song);
        intent.putExtra("param_now_playing", param_now_playing);
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
            putExtraMusicData(returnIntent);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        if (requestCode == SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                getData(data);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
}
