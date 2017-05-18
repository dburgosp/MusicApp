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

    int param_type, param_artist, param_album, param_genre, param_playlist;
    boolean now_playing;
    RelativeLayout nowPlayingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_list);

        // Get music data and parameters.
        getData();
        nowPlayingView = (RelativeLayout) findViewById(R.id.row_list_now_playing);
        if (!now_playing) nowPlayingView.setVisibility(View.GONE);
        else nowPlayingView.setVisibility(View.VISIBLE);

        // Songs to be listed.
        getSongs();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.row_list_recycler_view);
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 0, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                nowPlayingView.setVisibility(View.VISIBLE);
                ImageView nowPlayingImage = (ImageView) findViewById(R.id.row_list_now_playing_image);
                TextView nowPlayingTitle = (TextView) findViewById(R.id.row_list_now_playing_title);
                TextView nowPlayingSubtitle = (TextView) findViewById(R.id.row_list_now_playing_subtitle);

v.getT
                albumId = songsArrayList.get(position).getSongAlbumId() - 1;
                imageName = albumsArrayList.get(albumId).getAlbumImage();
                imageId = getResources().getIdentifier(imageName, "drawable", RowListActivity.this.getPackageName());
                drawable = RowListActivity.this.getResources().getDrawable(imageId);


                nowPlayingImage.setImageDrawable();
                now_playing = true;
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        now_playing = getIntent().getBooleanExtra("now_playing", false);
    }

    void getSongs() {
        int i, albumId, authorId, imageId, songId;
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
