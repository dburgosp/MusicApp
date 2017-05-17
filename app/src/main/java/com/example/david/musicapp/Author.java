package com.example.david.musicapp;

/**
 * Created by David on 16/05/2017.
 */

public class Author implements java.io.Serializable {
    private int authorId;
    private String authorName;
    private String authorImage;

    public Author(int authorId, String authorName, String authorImage) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
    }

    public int getAuthorId() { return this.authorId; }

    public String getAuthorName() { return this.authorName; }

    public String getAuthorImage() { return this.authorImage; }
}
