package com.example.david.musicapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ColumnListActivity extends AppCompatActivity {
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
    ArrayList<Element> elementsArrayList;

    int param_type, param_now_playing = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);

        // Get music data and parameters from previous activity.
        getData();
        RelativeLayout nowPlayingView = (RelativeLayout) findViewById(R.id.column_list_now_playing);
        if (param_now_playing < 0) nowPlayingView.setVisibility(View.GONE);
        else nowPlayingView.setVisibility(View.VISIBLE);

        // Get the list of elements to be listed.
        getSongs();

        // Compose the dynamic list of elements and define the onClick behaviour.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.column_list_recycler_view);
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent songsIntent = new Intent(ColumnListActivity.this, RowListActivity.class);
                switch (param_type) {
                    case 2: // List of artists.
                        songsIntent.putExtra("param_type", 6); // List of songs by artist.
                        songsIntent.putExtra("param_artist", position + 1);
                        break;

                    case 3: // List of albums.
                        songsIntent.putExtra("param_type", 7); // List of songs by album.
                        songsIntent.putExtra("param_album", position + 1);
                        break;

                    case 4: // List of genres.
                        songsIntent.putExtra("param_type", 8); // List of songs by music genre.
                        songsIntent.putExtra("param_genre", position + 1);
                        break;

                    case 5: // List of playlists.
                        songsIntent.putExtra("param_type", 9); // List of songs by playlist.
                        songsIntent.putExtra("param_playlist", position + 1);
                        break;
                }
                songsIntent.putExtra("param_now_playing", param_now_playing);
                putExtraMusicData(songsIntent);
                startActivity(songsIntent);
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    void getData() {
        albumsArrayList = (ArrayList<Album>) getIntent().getSerializableExtra("albumsArrayList");
        authorsArrayList = (ArrayList<Author>) getIntent().getSerializableExtra("authorsArrayList");
        musicGenresArrayList = (ArrayList<MusicGenre>) getIntent().getSerializableExtra("musicGenresArrayList");
        playlistsArrayList = (ArrayList<Playlist>) getIntent().getSerializableExtra("playlistsArrayList");
        playlistSongsArrayList = (ArrayList<PlaylistSong>) getIntent().getSerializableExtra("playlistSongsArrayList");
        songsArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songsArrayList");
        param_type = getIntent().getIntExtra("param_type", 2);
        param_now_playing = getIntent().getIntExtra("param_now_playing", -1);
    }

    void getSongs() {
        int i, n, albumId, authorId, imageId, genreId, playlistId;
        String imageName, authorName, albumName, title, subtitle;
        Drawable drawable;
        Element element;

        elementsArrayList = new ArrayList<Element>();
        switch (param_type) {
            case 2: // List of artists.
                for (i = 0; i < NUMBER_OF_AUTHORS; i++) {
                    // Image.
                    imageName = authorsArrayList.get(i).getAuthorImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = ColumnListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    title = authorsArrayList.get(i).getAuthorName();

                    // Subtitle.
                    n = 0;
                    authorId = authorsArrayList.get(i).getAuthorId();
                    for (Album album : albumsArrayList) {
                        if (album.getAlbumAuthorId() == authorId) n++;
                    }
                    subtitle = "Albums: " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }
                break;

            case 3: // List of albums.
                for (i = 0; i < NUMBER_OF_ALBUMS; i++) {
                    // Image.
                    imageName = albumsArrayList.get(i).getAlbumImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = ColumnListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    title = albumsArrayList.get(i).getAlbumName();

                    // Subtitle.
                    authorId = albumsArrayList.get(i).getAlbumAuthorId() - 1;
                    subtitle = authorsArrayList.get(authorId).getAuthorName();

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }
                break;

            case 4: // List of genres.
                for (i = 0; i < NUMBER_OF_GENRES; i++) {
                    // Image.
                    imageName = musicGenresArrayList.get(i).getGenreImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = ColumnListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    title = musicGenresArrayList.get(i).getGenreName();

                    // Subtitle.
                    n = 0;
                    genreId = musicGenresArrayList.get(i).getGenreId();
                    for (Song song : songsArrayList) {
                        if (song.getSongGenreId() == genreId) n++;
                    }
                    subtitle = "Songs: " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }
                break;

            case 5: // List of playlists.
                for (i = 0; i < NUMBER_OF_PLAYLISTS; i++) {
                    // Image.
                    imageName = playlistsArrayList.get(i).getPlaylistImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = ColumnListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    title = playlistsArrayList.get(i).getPlaylistName();

                    // Subtitle.
                    n = 0;
                    playlistId = playlistsArrayList.get(i).getPlaylistId();
                    for (PlaylistSong ps : playlistSongsArrayList) {
                        if (ps.getPlaylistId() == playlistId) n++;
                    }
                    subtitle = "Songs: " + n;

                    // Build current element.
                    element = new Element(0, title, subtitle, drawable);
                    elementsArrayList.add(element);
                }
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
    }
}
