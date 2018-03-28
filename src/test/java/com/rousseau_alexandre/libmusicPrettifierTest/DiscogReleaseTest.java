package com.rousseau_alexandre.libmusicPrettifierTest;

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

        JSONObject firstArtist = new JSONObject();
        firstArtist.put("name", "Red Pill");

        JSONObject secondArtist = new JSONObject();
        secondArtist.put("name", "L'Orange");

        JSONArray artists = new JSONArray();
        artists.put(firstArtist);
        artists.put(secondArtist);
        json.put("artists", artists);

        return json;
    }

    @Test
    public void testParseJsonObject() {
        JSONObject result = getJsonObject();
        DiscogRelease release = new DiscogRelease(result);

        Assert.assertEquals("Red Pill & L'Orange", release.getArtist());
    }

}
