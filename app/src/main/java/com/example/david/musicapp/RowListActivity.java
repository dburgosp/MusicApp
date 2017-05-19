package com.example.david.musicapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RowListActivity extends AppCompatActivity {
    final int NUMBER_OF_SONGS = 99;

    ArrayList<Album> albumsArrayList;
    ArrayList<Author> authorsArrayList;
    ArrayList<MusicGenre> musicGenresArrayList;
    ArrayList<Playlist> playlistsArrayList;
    ArrayList<PlaylistSong> playlistSongsArrayList;
    ArrayList<Song> songsArrayList;
    ArrayList<Element> elementsArrayList;

    int param_type, param_artist, param_album, param_genre, param_playlist, param_now_playing = 0;

    boolean now_playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_list);

        final RelativeLayout nowPlayingView = (RelativeLayout) findViewById(R.id.row_list_now_playing);
        final ImageView nowPlayingImage = (ImageView) findViewById(R.id.row_list_now_playing_image);
        final TextView nowPlayingTitle = (TextView) findViewById(R.id.row_list_now_playing_title);
        final TextView nowPlayingSubtitle = (TextView) findViewById(R.id.row_list_now_playing_subtitle);
        final ImageView nowPlayingButton = (ImageView) findViewById(R.id.row_list_now_playing_button);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.row_list_recycler_view);

        // Get music data and parameters.
        getData();
        if (param_now_playing < 0) nowPlayingView.setVisibility(View.GONE);
        else nowPlayingView.setVisibility(View.VISIBLE);

        // Songs to be listed.
        getSongs();

        // Compose the dynamic list of songs and define the onClick behaviour of each element.
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 0, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // Set up the "now playing" section.
                Toast toast = Toast.makeText(getApplicationContext(), "Play music", Toast.LENGTH_SHORT);
                toast.show();
                nowPlayingImage.setImageDrawable(elementsArrayList.get(position).getImage());
                nowPlayingTitle.setText(elementsArrayList.get(position).getFirstLine());
                nowPlayingSubtitle.setText(elementsArrayList.get(position).getSecondLine());
                nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_circle_outline_black_36dp));
                nowPlayingView.setVisibility(View.VISIBLE);
                param_now_playing = position;
                now_playing = true;
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Define behaviour of play/stop button.
        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when nowPlayingButton is clicked on.
            @Override
            public void onClick(View view) {
                if (now_playing) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Pause music", Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_play_circle_outline_black_36dp));
                    now_playing = false;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Play music", Toast.LENGTH_SHORT);
                    toast.show();
                    nowPlayingButton.setImageDrawable(getDrawable(R.drawable.ic_pause_circle_outline_black_36dp));
                    now_playing = true;
                }
            }
        });
    }

    void getData() {
        albumsArrayList = (ArrayList<Album>) getIntent().getSerializableExtra("albumsArrayList");
        authorsArrayList = (ArrayList<Author>) getIntent().getSerializableExtra("authorsArrayList");
        musicGenresArrayList = (ArrayList<MusicGenre>) getIntent().getSerializableExtra("musicGenresArrayList");
        playlistsArrayList = (ArrayList<Playlist>) getIntent().getSerializableExtra("playlistsArrayList");
        playlistSongsArrayList = (ArrayList<PlaylistSong>) getIntent().getSerializableExtra("playlistSongsArrayList");
        songsArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songsArrayList");
        param_type = getIntent().getIntExtra("param_type", 1);
        param_artist = getIntent().getIntExtra("param_artist", 1);
        param_album = getIntent().getIntExtra("param_album", 1);
        param_genre = getIntent().getIntExtra("param_genre", 1);
        param_playlist = getIntent().getIntExtra("param_playlist", 1);
        param_now_playing = getIntent().getIntExtra("param_now_playing", -1);
    }

    void getSongs() {
        int i, albumId, authorId, imageId, songId;
        String imageName, title, subtitle;
        Drawable drawable;
        Element element;

        elementsArrayList = new ArrayList<Element>();
        switch (param_type) {
            case 1: // Assorted list of songs.
                this.setTitle("All the songs");
                for (i = 0; i < NUMBER_OF_SONGS; i++) {
                    // SongId.
                    songId = songsArrayList.get(i).getSongId();

                    // Image.
                    albumId = songsArrayList.get(i).getSongAlbumId() - 1;
                    imageName = albumsArrayList.get(albumId).getAlbumImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                    drawable = RowListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    title = songsArrayList.get(i).getSongName();

                    // Subtitle.
                    authorId = albumsArrayList.get(albumId).getAlbumAuthorId() - 1;
                    subtitle = authorsArrayList.get(authorId).getAuthorName();

                    // Build current element.
                    element = new Element(songId, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }
                break;

            case 6: // List of songs by artist.
                this.setTitle(authorsArrayList.get(param_artist - 1).getAuthorName());
                for (Album album : albumsArrayList) {
                    if (album.getAlbumAuthorId() == param_artist) {
                        // Image.
                        imageName = album.getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = RowListActivity.this.getResources().getDrawable(imageId);

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
                    }
                }
                break;

            case 7: // List of songs by album.
                this.setTitle(albumsArrayList.get(param_album - 1).getAlbumName());
                for (Song song : songsArrayList) {
                    if (song.getSongAlbumId() == param_album) {
                        // SongId.
                        songId = song.getSongId();

                        // Image.
                        imageName = albumsArrayList.get(param_album - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = RowListActivity.this.getResources().getDrawable(imageId);

                        // Title.
                        title = song.getSongName();

                        // Subtitle.
                        subtitle = albumsArrayList.get(param_album - 1).getAlbumName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);
                    }
                }
                break;

            case 8: // List of songs by music genre.
                this.setTitle(musicGenresArrayList.get(param_genre - 1).getGenreName());
                for (Song song : songsArrayList) {
                    if (song.getSongGenreId() == param_genre) {
                        // SongId.
                        songId = song.getSongId();

                        // Image.
                        albumId = song.getSongAlbumId();
                        imageName = albumsArrayList.get(albumId - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = RowListActivity.this.getResources().getDrawable(imageId);

                        // Title.
                        title = song.getSongName();

                        // Subtitle.
                        subtitle = albumsArrayList.get(albumId - 1).getAlbumName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);
                    }
                }
                break;

            case 9: // List of songs by playlist.
                this.setTitle(playlistsArrayList.get(param_playlist - 1).getPlaylistName());
                for (PlaylistSong ps : playlistSongsArrayList) {
                    if (ps.getPlaylistId() == param_playlist) {
                        // SongId.
                        songId = ps.getSongId();

                        // Image.
                        albumId = songsArrayList.get(songId - 1).getSongAlbumId();
                        imageName = albumsArrayList.get(albumId - 1).getAlbumImage();
                        imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                        drawable = RowListActivity.this.getResources().getDrawable(imageId);

                        // Title.
                        title = songsArrayList.get(songId - 1).getSongName();

                        // Subtitle.
                        subtitle = albumsArrayList.get(albumId - 1).getAlbumName();

                        // Build current element.
                        element = new Element(songId, title, subtitle, drawable);
                        elementsArrayList.add(element);
                    }
                }
                break;
        }
    }
}
