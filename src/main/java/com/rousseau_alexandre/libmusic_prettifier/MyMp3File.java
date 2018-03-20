package com.rousseau_alexandre.libmusic_prettifier;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import java.util.Vector;

public class MyMp3File extends Mp3File {

    private ID3v1 id3;
    private Vector<MissingTag> missingTags = new Vector();

    public MyMp3File(String path) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);
        System.out.println(path);

        if (hasId3v1Tag()) {
            id3 = getId3v1Tag();

        } else if (hasId3v2Tag()) {
            id3 = getId3v2Tag();
            // @todo: do something here
        } else {
            // no tags found
        }
        findMissingTags();

    }

    public ID3v1 getId3() {
        return id3;
    }

    public Vector<MissingTag> getMissingTags() {
        return missingTags;
    }

    private void findMissingTags() {

        if (id3 == null) {
            missingTags.add(MissingTag.Id3);
            return;
        }

        if (id3.getAlbum() == null) {
            missingTags.add(MissingTag.Album);
        }

        if (id3.getArtist() == null) {
            missingTags.add(MissingTag.Artist);
        }

        if (id3.getGenreDescription() == null) {
            missingTags.add(MissingTag.GenreDescription);
        }

        if (id3.getYear() == null) {
            missingTags.add(MissingTag.Year);
        }

    }

}
