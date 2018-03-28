package com.rousseau_alexandre.libmusicPrettifier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represent a release from Discog API. Example:
 * https://api.discogs.com/releases/6879728
 */
public class DiscogRelease {

    private final String[] artists;

    public DiscogRelease(JSONObject result) {
        // setup artists
        JSONArray artistsArray = (JSONArray) result.get("artists");
        int nbArtists = artistsArray.length();
        this.artists = new String[nbArtists];
        for (int i = 0; i < nbArtists; i++) {
            JSONObject artist = artistsArray.getJSONObject(i);
            this.artists[i] = (String) artist.get("name");
        }
    }

    public String getArtist() {
        return String.join(" & ", artists);
    }

}
