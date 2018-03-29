package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMp3File extends Mp3File {

    private static final String RETAG_EXTENSION = ".retag";

    private ID3v1 currentID3;
    private ID3v24Tag newID3;

    public MyMp3File(String path) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);

        if (hasId3v1Tag()) {
            currentID3 = getId3v1Tag();

        } else if (hasId3v2Tag()) {
            currentID3 = getId3v2Tag();
            // @todo: do something here
        } else {
            // no tags found
        }

    }

    public ID3v1 getCurrentID3() {
        return currentID3;
    }

    public ID3v24Tag getNewID3() {
        return newID3;
    }

    /**
     * For testing purpose
     *
     * @param newID3
     */
    public void setNewID3(ID3v24Tag newID3) {
        this.newID3 = newID3;
    }

    public void update() throws IOException, NotSupportedException {
        this.setNewID3(newID3);
        this.save(getRetagFilename());

        File origin = new File(this.getFilename());
        File retag = new File(getRetagFilename());

        origin.delete();
        retag.renameTo(origin);
    }

    private String getRetagFilename() {
        return this.getFilename() + RETAG_EXTENSION;
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

}
