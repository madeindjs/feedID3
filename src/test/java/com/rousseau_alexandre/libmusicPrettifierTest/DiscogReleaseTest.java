package com.rousseau_alexandre.libmusicPrettifierTest;

import com.mpatric.mp3agic.ID3v24Tag;
import com.rousseau_alexandre.libmusicPrettifier.DiscogRelease;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author apprenant
 */
public class DiscogReleaseTest {

    private static final String JSON_PATH = "src/test/resources/release.json";

    /**
     * Get a JSONObject who represent a result from Discog API
     *
     * @return
     */
    private DiscogRelease getJsonObject() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(JSON_PATH)));
        JSONObject json = new JSONObject(content);

        return new DiscogRelease(json);
    }

    @Test
    public void testDiscogRelease() throws IOException {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);
    }

    @Test
    public void testParseJsonObject() throws IOException {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);

        Assert.assertEquals("Red Pill", release.getArtist());
        Assert.assertEquals("https://api.discogs.com/artists/3835396", release.getArtistUrl());
        Assert.assertEquals(7, release.getGenre());
        Assert.assertEquals("Hip Hop", release.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", release.getTitle());
        Assert.assertEquals(2015, release.getYear());
    }

    @Test
    public void testToId3() throws IOException {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);
        ID3v24Tag id3 = release.toID3();

        Assert.assertEquals("Red Pill", id3.getArtist());
        Assert.assertEquals("https://api.discogs.com/artists/3835396", id3.getArtistUrl());
        Assert.assertEquals(7, id3.getGenre());
        Assert.assertEquals("Hip-Hop", id3.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", id3.getTitle());
        Assert.assertEquals("2015", id3.getYear());
    }

}
