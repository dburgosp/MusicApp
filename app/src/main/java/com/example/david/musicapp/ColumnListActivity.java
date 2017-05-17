package com.example.david.musicapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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

    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i, albumId, authorId, imageId;
        String imageName, authorName, albumName;
        Drawable drawable;
        Element element;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);

        // Get music data and parameters.
        albumsArrayList = (ArrayList<Album>) getIntent().getSerializableExtra("albumsArrayList");
        authorsArrayList = (ArrayList<Author>) getIntent().getSerializableExtra("authorsArrayList");
        musicGenresArrayList = (ArrayList<MusicGenre>) getIntent().getSerializableExtra("musicGenresArrayList");
        playlistsArrayList = (ArrayList<Playlist>) getIntent().getSerializableExtra("playlistsArrayList");
        playlistSongsArrayList = (ArrayList<PlaylistSong>) getIntent().getSerializableExtra("playlistSongsArrayList");
        songsArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songsArrayList");
        type = getIntent().getIntExtra("type", 1);

        // Songs to be listed.
        switch (type) {
            case 6: // List of albums.
                elementsArrayList = new ArrayList<Element>();
                for (i = 0; i < NUMBER_OF_ALBUMS; i++) {
                    // Image.
                    imageName = albumsArrayList.get(i).getAlbumImage();
                    imageId = getResources().getIdentifier(imageName, "drawable", ColumnListActivity.this.getPackageName());
                    drawable = ColumnListActivity.this.getResources().getDrawable(imageId);

                    // Title.
                    albumName = albumsArrayList.get(i).getAlbumName();

                    // Subtitle.
                    authorId = albumsArrayList.get(i).getAlbumAuthorId() - 1;
                    authorName = authorsArrayList.get(authorId).getAuthorName();

                    // Build current element.
                    element = new Element(albumName, authorName, drawable);
                    elementsArrayList.add(element);
                }
                break;
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.column_list_recycler_view);
        recyclerView.setAdapter(new RecyclerViewElement(elementsArrayList, 1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast toast = Toast.makeText(ColumnListActivity.this, "Play song #" + String.valueOf(position), Toast.LENGTH_SHORT);
                toast.show();
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
