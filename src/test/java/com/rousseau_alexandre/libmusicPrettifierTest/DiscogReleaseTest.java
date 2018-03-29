package com.rousseau_alexandre.libmusicPrettifierTest;

import com.mpatric.mp3agic.ID3v24Tag;
import com.rousseau_alexandre.libmusicPrettifier.DiscogRelease;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author apprenant
 */
public class DiscogReleaseTest {

    /**
     * Get a JSONObject who represent a result from Discog API
     *
     * @return
     */
    private JSONObject getJsonObject() {
        JSONObject json = new JSONObject();
        // artists
        {
            JSONObject firstArtist = new JSONObject();
            firstArtist.put("name", "Red Pill");
            firstArtist.put("resource_url", "https://api.discogs.com/artists/3835396");

            JSONObject secondArtist = new JSONObject();
            secondArtist.put("name", "L'Orange");

            JSONArray artists = new JSONArray();
            artists.put(firstArtist);
            artists.put(secondArtist);
            json.put("artists", artists);
        }
        // genres
        {
            JSONArray genres = new JSONArray();
            genres.put("Hip Hop");
            genres.put("Rap");
            json.put("genres", genres);
        }
        // title
        {
            json.put("title", "Look What This World Did to Us");
        }

        return new DiscogRelease(json);
    }

    @Test
    public void testDiscogRelease() {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);
    }

    @Test
    public void testParseJsonObject() {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);

        Assert.assertEquals("Red Pill & L'Orange", release.getArtist());
        Assert.assertEquals(7, release.getGenre());
        Assert.assertEquals("Hip Hop / Rap", release.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", release.getTitle());
    }

    @Test
    public void testToId3() {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);
        ID3v24Tag id3 = release.toID3();

        Assert.assertEquals("Red Pill & L'Orange", id3.getArtist());
        Assert.assertEquals(7, id3.getGenre());
        Assert.assertEquals("Hip-Hop", id3.getGenreDescription());
        Assert.assertEquals("Look What This World Did to Us", id3.getTitle());
    }

}
