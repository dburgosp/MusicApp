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
import java.util.Collections;
import java.util.Comparator;

public class RowListActivity extends AppCompatActivity {
    // Constants.
    final int NUMBER_OF_SONGS = 99;
    final int ROW_LIST_ACTIVITY = 3;

    // Global variables.
    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    ArrayList<Element> elementsArrayList;
    RelativeLayout nowPlayingView;
    ImageView nowPlayingImage, nowPlayingButton, mainImage;
    TextView nowPlayingTitle, nowPlayingSubtitle;
    RecyclerView recyclerView;
    Button buyMusicButton;

    // Shared data with other activities.
    int param_type, param_artist, param_album, param_genre, param_playlist, param_now_playing_song = -1;
    boolean param_now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_list);

        // Get views from the current layout.
        nowPlayingView = (RelativeLayout) findViewById(R.id.row_list_now_playing);
        nowPlayingImage = (ImageView) findViewById(R.id.row_list_now_playing_image);
        nowPlayingTitle = (TextView) findViewById(R.id.row_list_now_playing_title);
        nowPlayingSubtitle = (TextView) findViewById(R.id.row_list_now_playing_subtitle);
        nowPlayingButton = (ImageView) findViewById(R.id.row_list_now_playing_button);
        mainImage = (ImageView) findViewById(R.id.row_list_cover);
        recyclerView = (RecyclerView) findViewById(R.id.row_list_recycler_view);
        buyMusicButton = (Button) findViewById(R.id.row_list_buy_button);

        // Get music data and parameters.
        getData(getIntent());

        // Get songs to be listed and sets main image, if necessary.
        getSongs();

        // Compose the dynamic list of songs and define the onClick behaviour of each element.
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 0, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // Set up the "now playing" section.
                Toast toast = Toast.makeText(getApplicationContext(), R.string.play_music, Toast.LENGTH_SHORT);
                toast.show();
                param_now_playing_song = elementsArrayList.get(position).getSongId() - 1;
                param_now_playing = true;
                setNowPlayingView();
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Define behaviour of the "Now playing" section.
        nowPlayingView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when any element into the nowPlayingSection
            // RelativeLayout is clicked on.
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(RowListActivity.this, NowPlayingActivity.class);
                putExtraMusicData(nowPlayingIntent);
                startActivityForResult(nowPlayingIntent, ROW_LIST_ACTIVITY);
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
     * Prepare the list of songs to show in this activity, depending on the sorting parameter
     * param_type, and shows/hide main image at the top of this activity.
     */
    void getSongs() {
        int i, albumId, authorId, imageId, songId, genreId, playlistId;
        String imageName, title, subtitle;
        Drawable drawable;
        Element element;

        elementsArrayList = new ArrayList<Element>();
        switch (param_type) {
            case 1: // Assorted list of songs.
                for (i = 0; i < NUMBER_OF_SONGS; i++) {
                    // SongId.
                    songId = songsArrayList.get(i).getSongId();

                    // Image.
                    albumId = songsArrayList.get(i).getSongAlbumId() - 1;
                    imageName = albumsArrayList.get(albumId).getAlbumImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                    drawable = getDrawable(imageId);

                    // Title.
                    title = songsArrayList.get(i).getSongName();

                    // Subtitle.
                    authorId = albumsArrayList.get(albumId).getAlbumAuthorId() - 1;
                    subtitle = authorsArrayList.get(authorId).getAuthorName();

                    // Build current element.
                    element = new Element(songId, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }

                // Order array of elements.
                Collections.sort(elementsArrayList, new elementComparator());

                // Hide image view.
                mainImage.setVisibility(View.GONE);

                // Set title for this activity.
                this.setTitle(R.string.category_songs);
                break;

            case 6: // List of songs by artist.
                for (Album album : albumsArrayList) {
                    if (album.getAlbumAuthorId() == param_artist) {
                        // Image.
                        imageName = album.getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = getDrawable(imageId);

                        // Subtitle.
                        subtitle = album.getAlbumName();

                        albumId = album.getAlbumId();
                        for (Song song : songsArrayList) {
                            if (song.getSongAlbumId() == albumId) {
                                // SongId.
                                songId = song.getSongId();

                                // Title.
                                title = song.getSongName();

                                // Build current element.
                                element = new Element(songId, title, subtitle, drawable);
                                elementsArrayList.add(element);
                            }
                        }

                        // Show and set image view.
                        authorId = album.getAlbumAuthorId() - 1;
                        imageName = authorsArrayList.get(authorId).getAuthorImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        mainImage.setImageDrawable(getDrawable(imageId));
                        mainImage.setVisibility(View.VISIBLE);
                    }
                }

                // Order array of elements.
                Collections.sort(elementsArrayList, new elementComparator());

                // Set title for this activity.
                this.setTitle(authorsArrayList.get(param_artist - 1).getAuthorName());
                break;

            case 7: // List of songs by album.
                for (Song song : songsArrayList) {
                    if (song.getSongAlbumId() == param_album) {
                        // SongId.
                        songId = song.getSongId();

                        // Image.
                        imageName = albumsArrayList.get(param_album - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = getDrawable(imageId);

                        // Title.
                        title = song.getSongName();

                        // Subtitle.
                        authorId = albumsArrayList.get(param_album - 1).getAlbumAuthorId() - 1;
                        subtitle = authorsArrayList.get(authorId).getAuthorName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);

                        // Show and set image view.
                        albumId = song.getSongAlbumId() - 1;
                        imageName = albumsArrayList.get(albumId).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        mainImage.setImageDrawable(getDrawable(imageId));
                        mainImage.setVisibility(View.VISIBLE);
                    }
                }

                // Order array of elements.
                Collections.sort(elementsArrayList, new elementComparator());

                // Set title for this activity.
                this.setTitle(albumsArrayList.get(param_album - 1).getAlbumName());
                break;

            case 8: // List of songs by music genre.
                for (Song song : songsArrayList) {
                    if (song.getSongGenreId() == param_genre) {
                        // SongId.
                        songId = song.getSongId();

                        // Image.
                        albumId = song.getSongAlbumId();
                        imageName = albumsArrayList.get(albumId - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = getDrawable(imageId);

                        // Title.
                        title = song.getSongName();

                        // Subtitle.
                        subtitle = albumsArrayList.get(albumId - 1).getAlbumName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);

                        // Show and set image view.
                        genreId = song.getSongGenreId() - 1;
                        imageName = musicGenresArrayList.get(genreId).getGenreImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        mainImage.setImageDrawable(getDrawable(imageId));
                        mainImage.setVisibility(View.VISIBLE);
                    }
                }

                // Order array of elements.
                Collections.sort(elementsArrayList, new elementComparator());

                // Set title for this activity.
                this.setTitle(musicGenresArrayList.get(param_genre - 1).getGenreName());
                break;

            case 9: // List of songs by playlist.
                for (PlaylistSong ps : playlistSongsArrayList) {
                    if (ps.getPlaylistId() == param_playlist) {
                        // SongId.
                        songId = ps.getSongId();

                        // Image.
                        albumId = songsArrayList.get(songId - 1).getSongAlbumId();
                        imageName = albumsArrayList.get(albumId - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = getDrawable(imageId);

                        // Title.
                        title = songsArrayList.get(songId - 1).getSongName();

                        // Subtitle.
                        subtitle = albumsArrayList.get(albumId - 1).getAlbumName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);

                        // Show and set image view.
                        playlistId = ps.getPlaylistId() - 1;
                        imageName = playlistsArrayList.get(playlistId).getPlaylistImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        mainImage.setImageDrawable(getDrawable(imageId));
                        mainImage.setVisibility(View.VISIBLE);
                    }
                }

                // Order array of elements.
                Collections.sort(elementsArrayList, new elementComparator());

                // Set title for this activity.
                this.setTitle(playlistsArrayList.get(param_playlist - 1).getPlaylistName());
                break;
        }
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
            int imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
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
        if (requestCode == ROW_LIST_ACTIVITY) {
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

    // Comparator for ordering arrays of elements.
    class elementComparator implements Comparator<Element> {
        public int compare(Element a, Element b) {
            return a.getFirstLine().compareTo(b.getFirstLine());
        }
    }
}
