feedID3
=======

Improve MP3's ID3 tags using [Discogs](https://www.discogs.com) API.

> Currently on alpha stage, use it at your own risk

Usage
-----

* Correct your mp3 file

~~~java
import com.github.madeindjs.feedID3.DiscogConsumerNotSetException;
import com.github.madeindjs.feedID3.MyMp3File;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException, DiscogConsumerNotSetException {
        // set API key
        Discog.CONSUMER_KEY = "<YOUR_CONSUMER_KEY>";
        Discog.CONSUMER_SECRET = "<YOUR_CONSUMER_SECRET>";

        // Open file
        MyMp3File file = new MyMp3File("src/test/resources/empty.mp3");
        // fetch informations from Discog API
        file.getInformations();
        // read information fetched
        ID3v24Tag id3 = file.getNewID3();
        System.out.println(String.format("New artist: %s", id3.getArtist()));
        // overwide file
        file.update();
    }

}
~~~

* Correct all your librairy **(This will overwride all your mp3 tags)**

~~~java
import com.github.madeindjs.feedID3.DiscogConsumerNotSetException;
import com.github.madeindjs.feedID3.Librairy;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException, DiscogConsumerNotSetException {
        // set API key
        Discog.CONSUMER_KEY = "<YOUR_CONSUMER_KEY>";
        Discog.CONSUMER_SECRET = "<YOUR_CONSUMER_SECRET>";

        Librairy librairy = new Librairy("src/test/resources");
        librairy.correct();
    }

}
~~~
