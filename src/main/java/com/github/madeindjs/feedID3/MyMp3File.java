package com.github.madeindjs.feedID3;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMp3File extends Mp3File {

    /**
     * Extension of the file
     */
    private static final String RETAG_EXTENSION = ".retag";
    /**
     * Represent the current ID3 tag for the file
     */
    private ID3v1 currentID3;
    /**
     * Represent the new ID3 tag with informations fetched from Discog API
     */
    private ID3v24Tag newID3 = new ID3v24Tag();

    /**
     * Open a new MP3 file
     *
     * @param path
     * @throws IOException
     * @throws UnsupportedTagException
     * @throws InvalidDataException
     */
    public MyMp3File(String path) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);

        if (hasId3v2Tag()) {
            currentID3 = getId3v2Tag();
        } else if (hasId3v1Tag()) {
            currentID3 = getId3v1Tag();
        }
    }

    /**
     * @return the current ID3 tag linked to this file
     */
    public ID3v1 getCurrentID3() {
        return currentID3;
    }

    /**
     * @return get the ID3 tag who'll be saved in the file
     */
    public ID3v24Tag getNewID3() {
        return newID3;
    }

    @Override
    public void setId3v1Tag(ID3v1 id3v1) {
        super.setId3v1Tag(id3v1);
        this.currentID3 = id3v1;
    }

    @Override
    public void setId3v2Tag(ID3v2 id3v2) {
        super.setId3v2Tag(id3v2);
        this.currentID3 = id3v2;
    }

    /**
     * For testing purpose
     *
     * @param newID3
     */
    public void setNewID3(ID3v24Tag newID3) {
        this.newID3 = newID3;
    }

    /**
     * Update ID3 tag with `newID3` variable
     *
     * @throws IOException
     * @throws NotSupportedException
     */
    public void update() throws IOException, NotSupportedException {
        // this.setId3v2Tag(newID3);
        this.setId3v1Tag(newID3);
        this.currentID3 = newID3;
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
     * Get the search String from ID3 if it contains somes basics information
     * (title & artists) or from file if ID is not complete
     *
     * @return
     */
    public String getSearchString() {
        if (this.currentID3 != null && this.currentID3.getArtist() != null && this.currentID3.getTitle() != null) {
            return getSearchStringFromID3();
        } else {
            return getSearchStringFromFile();
        }
    }

    public String getSearchStringFromID3() {
        return String.format("%s %s", currentID3.getArtist(), currentID3.getTitle());
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

    public DiscogResults searchResults() {
        Discog api = new Discog();
        String searchString = getSearchString();
        try {
            return api.search(searchString);
        } catch (IOException | DiscogConsumerNotSetException ex) {
            Logger.getLogger(MyMp3File.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Send a request to Discog API. It will gess informations to search from
     * the current informations contained in ID3 tag or from the filename.
     *
     * If response from Discog API contains responses, it will take the first.
     *
     * @return `true` if success
     */
    public boolean getInformations() throws MalformedURLException, DiscogConsumerNotSetException {
        DiscogResults results = searchResults();

        if (results != null) {
            Iterator<DiscogRelease> iterator = results.iterator();
            if (iterator.hasNext()) {
                newID3 = iterator.next().toID3();
                return true;
            }
        }

        return false;
    }

}
