package com.github.madeindjs.feedID3;

import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        Discog.CONSUMER_KEY = "xEnspHdzbJFKuGlvlNde";
        Discog.CONSUMER_SECRET = "jtgMHnbpRaGbXEDBXimCDddFqLtzZTAG";

        // Open file
        MyMp3File file;
        try {
            // open file
            file = new MyMp3File("src/test/resources/empty.mp3");

            // find & set first result from Discog API
            file.getInformations();
            ID3v24Tag id3 = file.getNewID3();
            file.update();

            // find all results from discog API
            DiscogResults searchResults = file.searchResults();
            // iterate from results
            Iterator<DiscogRelease> iterator = searchResults.iterator();
            while (iterator.hasNext()) {
                DiscogRelease release = iterator.next();
                System.out.println(String.format(
                        "%s - %s",
                        release.getArtist(),
                        release.getTitle()
                ));
            }

        } catch (IOException e) {
            System.err.println("Can't read / write MP3 file");
        } catch (UnsupportedTagException | InvalidDataException | NotSupportedException e) {
            System.err.println("Can't read / write ID3 Tag");
        } catch (DiscogConsumerNotSetException e) {
            System.err.println("Consumer key / secret not valid");
        }

    }

}
