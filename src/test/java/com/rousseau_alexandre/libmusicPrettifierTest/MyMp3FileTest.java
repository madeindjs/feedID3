package com.rousseau_alexandre.libmusicPrettifierTest;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.rousseau_alexandre.libmusicPrettifier.MissingTag;
import com.rousseau_alexandre.libmusicPrettifier.MyMp3File;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyMp3FileTest {

    private static final String MP3_SOURCE_FILEPATH = "src/test/resources/empty.mp3";
    private static final String MP3_FILEPATH = "src/test/resources/01 - Chris Orrick - Ten Year Party.mp3";

    @Before
    public void copyFile() throws IOException {
        File from = new File(MP3_SOURCE_FILEPATH);
        File to = new File(MP3_FILEPATH);
        Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @After
    public void removeFile() throws IOException {
        Files.delete(new File(MP3_FILEPATH).toPath());
    }

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

    @Test
    public void testGetSearchStringFromFile() throws IOException, UnsupportedTagException, InvalidDataException {
        MyMp3File file = new MyMp3File(MP3_FILEPATH);
        Assert.assertEquals("Chris Orrick Ten Year Party", file.getSearchStringFromFile());
    }

    @Test
    public void testCorrectFile() throws IOException, UnsupportedTagException, InvalidDataException {
        MyMp3File file = new MyMp3File(MP3_FILEPATH);
        Assert.assertEquals("Chris Orrick Ten Year Party", file.getSearchStringFromFile());
    }

}
