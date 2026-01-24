package com.serenecandy.models;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

import java.io.File;

public class Track {
    private String title;
    private String artist;
    private String album;
    private String path;

    public Track(String title, String artist, String album, String path) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
    }

    // ✅ Factory-style method: create Track directly from an audio file
    public static Track fromFile(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            String title  = tag != null ? tag.getFirst(FieldKey.TITLE)  : file.getName();
            String artist = tag != null ? tag.getFirst(FieldKey.ARTIST) : "Unknown Artist";
            String album  = tag != null ? tag.getFirst(FieldKey.ALBUM)  : "Unknown Album";

            return new Track(title, artist, album, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image getAlbumCover() {
        try {
            AudioFile f = AudioFileIO.read(new File(path));
            Tag tag = f.getTag();
            if(tag != null && tag.getFirstArtwork() != null) {
                byte[] bytes = tag.getFirstArtwork().getBinaryData();
                return new Image(new ByteArrayInputStream(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // fallback if no cover
    }


    // Getters
    public String getTitle()  { return title; }
    public String getArtist() { return artist; }
    public String getAlbum()  { return album; }
    public String getPath()   { return path; }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
