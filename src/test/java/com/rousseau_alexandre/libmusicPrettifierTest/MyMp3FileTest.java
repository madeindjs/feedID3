package com.rousseau_alexandre.libmusicPrettifierTest;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.rousseau_alexandre.libmusicPrettifier.MissingTag;
import com.rousseau_alexandre.libmusicPrettifier.MyMp3File;
import java.io.IOException;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class MyMp3FileTest {

    private static final String MP3_FILEPATH = "src/test/resources/file.mp3";

    @Test
    public void testMyMp3File() {
        try {
            new MyMp3File(MP3_FILEPATH);
        } catch (Exception e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void testMyMp3FileErrros() throws IOException, IOException, UnsupportedTagException, InvalidDataException {
        MyMp3File file = new MyMp3File(MP3_FILEPATH);
        Vector<MissingTag> missings = file.getMissingTags();
        Assert.assertTrue(missings.contains(MissingTag.Album));
        Assert.assertTrue(missings.contains(MissingTag.Artist));
        Assert.assertTrue(missings.contains(MissingTag.GenreDescription));
        Assert.assertTrue(missings.contains(MissingTag.Year));
    }

}
