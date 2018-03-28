package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.ID3v24Tag;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is class is supposed to parse JSON given from Discog API. Example:
 * https://api.discogs.com/releases/6879728
 */
public class DiscogRelease {

    private final String[] artists;
    private final String[] genres;

    public DiscogRelease(JSONObject result) {
        // artists
        {
            JSONArray artistsArray = (JSONArray) result.get("artists");
            int length = artistsArray.length();
            this.artists = new String[length];
            for (int i = 0; i < length; i++) {
                JSONObject artist = artistsArray.getJSONObject(i);
                this.artists[i] = (String) artist.get("name");
            }
        }
        // genres
        {
            JSONArray genresArray = (JSONArray) result.get("genres");
            int length = genresArray.length();
            this.genres = new String[length];
            for (int i = 0; i < length; i++) {
                this.genres[i] = (String) genresArray.getString(i);
            }
        }
    }

    public String getArtist() {
        return String.join(" & ", artists);
    }

    public String getGenreDescription() {
        return String.join(" / ", genres);
    }

    public ID3v24Tag toID3() {
        ID3v24Tag id3 = new ID3v24Tag();

        id3.setArtist(getArtist());
        id3.setGenreDescription(getGenreDescription());

        return id3;
    }

}
