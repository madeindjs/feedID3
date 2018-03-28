package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMp3File extends Mp3File {

    private ID3v1 id3;
    private Vector<MissingTag> missingTags = new Vector();

    public MyMp3File(String path) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);

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

    /**
     * Should extract name of title from the filename: remove extension, remove
     * leading number
     *
     * @return
     */
    public String getSearchStringFromFile() {
        File localFile = new File(getFilename());
        String name = localFile.getName();
        // remove extension
        name = name.replace(".mp3", "");
        // remove leading number
        name = name.replaceAll("[0-9]+", "");
        // remove leading number
        name = name.replaceAll(" +- +", " ");
        // remove leading space
        name = name.trim();

        return name;
    }

    public Vector<MissingTag> getMissingTags() {
        return missingTags;
    }

    public void getInformations() {
        Discog api = new Discog();
        try {
            DiscogRelease result = api.search(getSearchStringFromFile());
            if (result != null) {
                // todo: update ID3 tag
            }
        } catch (IOException ex) {
            Logger.getLogger(MyMp3File.class.getName()).log(Level.SEVERE, null, ex);
        }
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
