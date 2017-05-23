package com.example.david.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColumnListActivity extends AppCompatActivity {
    // Constants.
    final int NUMBER_OF_GENRES = 5;
    final int NUMBER_OF_AUTHORS = 6;
    final int NUMBER_OF_ALBUMS = 7;
    final int NUMBER_OF_PLAYLISTS = 2;
    final int COLUMN_LIST_ACTIVITY = 2;

    // Global variable.
    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    ArrayList<Element> elementsArrayList;
    RelativeLayout nowPlayingView;
    ImageView nowPlayingImage, nowPlayingButton;
    TextView nowPlayingTitle, nowPlayingSubtitle;
    RecyclerView recyclerView;
    Button buyMusicButton;

    // Shared data with other activities.
    int param_type, param_artist, param_album, param_genre, param_playlist, param_now_playing_song = -1;
    boolean param_now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);

        // Get views from the current layout.
        nowPlayingView = (RelativeLayout) findViewById(R.id.column_list_now_playing);
        nowPlayingImage = (ImageView) findViewById(R.id.column_list_now_playing_image);
        nowPlayingTitle = (TextView) findViewById(R.id.column_list_now_playing_title);
        nowPlayingSubtitle = (TextView) findViewById(R.id.column_list_now_playing_subtitle);
        nowPlayingButton = (ImageView) findViewById(R.id.column_list_now_playing_button);
        recyclerView = (RecyclerView) findViewById(R.id.column_list_recycler_view);
        buyMusicButton = (Button) findViewById(R.id.column_list_buy_button);

        // Get music data and parameters from previous activity.
        getData(getIntent());

        // Get the list of elements to be listed.
        getElements();

        // Compose the dynamic list of elements and define the onClick behaviour.
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent songsIntent = new Intent(ColumnListActivity.this, RowListActivity.class);
                putExtraMusicData(songsIntent);
                switch (param_type) {
                    case 2: // List of artists.
                    case 6: // List of songs by artist.
                        songsIntent.putExtra("param_type", 6); // List of songs by artist.
                        songsIntent.putExtra("param_artist", position + 1);
                        break;

                    case 3: // List of albums.
                    case 7: // List of songs by album.
                        songsIntent.putExtra("param_type", 7); // List of songs by album.
                        songsIntent.putExtra("param_album", position + 1);
                        break;

                    case 4: // List of genres.
                    case 8: // List of songs by music genre.
                        songsIntent.putExtra("param_type", 8); // List of songs by music genre.
                        songsIntent.putExtra("param_genre", position + 1);
                        break;

                    case 5: // List of playlists.
                    case 9: // List of songs by playlist.
                        songsIntent.putExtra("param_type", 9); // List of songs by playlist.
                        songsIntent.putExtra("param_playlist", position + 1);
                        break;
                }
                startActivityForResult(songsIntent, COLUMN_LIST_ACTIVITY);
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Define behaviour of the "Now playing" section.
        nowPlayingView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when any element into the nowPlayingSection
            // RelativeLayout is clicked on.
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(ColumnListActivity.this, NowPlayingActivity.class);
                putExtraMusicData(nowPlayingIntent);
                startActivityForResult(nowPlayingIntent, COLUMN_LIST_ACTIVITY);
            }
        });

        // Behaviour of play/stop button.
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

        // Behaviour of "buy music" button.
        buyMusicButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.buy_music, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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

        // Hide/show "now playing" section.
        setNowPlayingView();
    }

    /**
     * Prepare the list of elements to show in this activity, depending on the sorting parameter
     * param_type.
     */
    void getElements() {
        int i, n, authorId, imageId, genreId, playlistId;
        String imageName, title, subtitle;
        Drawable drawable;
        Element element;

        elementsArrayList = new ArrayList<Element>();
        switch (param_type) {
            case 2: // List of artists.
                for (i = 0; i < NUMBER_OF_AUTHORS; i++) {
                    // Image.
                    imageName = authorsArrayList.get(i).getAuthorImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = getDrawable(imageId);

                    // Title.
                    title = authorsArrayList.get(i).getAuthorName();

                    // Subtitle.
                    n = 0;
                    authorId = authorsArrayList.get(i).getAuthorId();
                    for (Album album : albumsArrayList) {
                        if (album.getAlbumAuthorId() == authorId) n++;
                    }
                    subtitle = getResources().getString(R.string.number_of_albums) + ": " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }

                // Set title for this activity.
                this.setTitle(R.string.category_artists);
                break;

            case 3: // List of albums.
                for (i = 0; i < NUMBER_OF_ALBUMS; i++) {
                    // Image.
                    imageName = albumsArrayList.get(i).getAlbumImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = getDrawable(imageId);

                    // Title.
                    title = albumsArrayList.get(i).getAlbumName();

                    // Subtitle.
                    authorId = albumsArrayList.get(i).getAlbumAuthorId() - 1;
                    subtitle = authorsArrayList.get(authorId).getAuthorName();

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }

                // Set title for this activity.
                this.setTitle(R.string.category_albums);
                break;

            case 4: // List of genres.
                for (i = 0; i < NUMBER_OF_GENRES; i++) {
                    // Image.
                    imageName = musicGenresArrayList.get(i).getGenreImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = getDrawable(imageId);

                    // Title.
                    title = musicGenresArrayList.get(i).getGenreName();

                    // Subtitle.
                    n = 0;
                    genreId = musicGenresArrayList.get(i).getGenreId();
                    for (Song song : songsArrayList) {
                        if (song.getSongGenreId() == genreId) n++;
                    }
                    subtitle = getResources().getString(R.string.number_of_songs) + ": " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }

                // Set title for this activity.
                this.setTitle(R.string.category_genres);
                break;

            case 5: // List of playlists.
                for (i = 0; i < NUMBER_OF_PLAYLISTS; i++) {
                    // Image.
                    imageName = playlistsArrayList.get(i).getPlaylistImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = getDrawable(imageId);

                    // Title.
                    title = playlistsArrayList.get(i).getPlaylistName();

                    // Subtitle.
                    n = 0;
                    playlistId = playlistsArrayList.get(i).getPlaylistId();
                    for (PlaylistSong ps : playlistSongsArrayList) {
                        if (ps.getPlaylistId() == playlistId) n++;
                    }
                    subtitle = getResources().getString(R.string.number_of_songs) + ": " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }

                // Set title for this activity.
                this.setTitle(R.string.category_playlists);
                break;
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

        intent.putExtra("param_type", param_type);
        intent.putExtra("param_artist", param_artist);
        intent.putExtra("param_album", param_album);
        intent.putExtra("param_genre", param_genre);
        intent.putExtra("param_playlist", param_playlist);
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
            int imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
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
        if (requestCode == COLUMN_LIST_ACTIVITY) {
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
