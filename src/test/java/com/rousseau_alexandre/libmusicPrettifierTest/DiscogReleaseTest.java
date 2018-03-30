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
    private static final String IMAGE_URL = "https://img.discogs.com/G1-XpvxNjMmcU_HRoshplE-jxGY=/fit-in/600x600/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-6879728-1428607084-9223.jpeg.jpg";

    /**
     * Get a JSONObject who represent a result from Discog API
     *
     * @return
     */
    public static DiscogRelease getDiscogRelease() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(JSON_PATH)));
        JSONObject json = new JSONObject(content);

        return new DiscogRelease(json);
    }

    @Test
    public void testDiscogRelease() throws IOException {
        JSONObject result = getDiscogRelease();
        DiscogRelease release = new DiscogRelease(result);
    }

    @Test
    public void testParseJsonObject() throws IOException {
        JSONObject result = getDiscogRelease();
        DiscogRelease release = new DiscogRelease(result);

        Assert.assertEquals("Red Pill", release.getArtist());
        Assert.assertEquals("https://api.discogs.com/artists/3835396", release.getArtistUrl());
        Assert.assertEquals(7, release.getGenre());
        Assert.assertEquals("Hip Hop", release.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", release.getTitle());
        Assert.assertEquals("Look What This World Did to Us", release.getAlbum());
        Assert.assertEquals(2015, release.getYear());
    }

    @Test
    public void testToId3() throws IOException {
        JSONObject result = getDiscogRelease();
        DiscogRelease release = new DiscogRelease(result);
        ID3v24Tag id3 = release.toID3();

        Assert.assertEquals("Red Pill", id3.getArtist());
        Assert.assertEquals("https://api.discogs.com/artists/3835396", id3.getArtistUrl());
        Assert.assertEquals(7, id3.getGenre());
        Assert.assertEquals("Hip-Hop", id3.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", id3.getTitle());
        Assert.assertEquals("Look What This World Did to Us", id3.getAlbum());
        Assert.assertEquals("2015", id3.getYear());
    }

    @Test
    public void getImage() throws IOException {
        JSONObject result = getDiscogRelease();
        DiscogRelease release = new DiscogRelease(result);

        // verify that URL is scraped
        String imageUrl = release.getImageUrl();
        System.out.println(imageUrl);
        Assert.assertEquals(IMAGE_URL, imageUrl);

        // verify that image is generated
        ID3v24Tag id3 = release.toID3();
        byte[] image = id3.getAlbumImage();
        Assert.assertNotNull(id3.getAlbumImage());
    }

}
