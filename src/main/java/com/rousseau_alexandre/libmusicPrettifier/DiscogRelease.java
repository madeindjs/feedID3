package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.ID3v24Tag;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is class is supposed to parse JSON given from Discog API. Example:
 * https://api.discogs.com/releases/6879728
 */
public class DiscogRelease extends JSONObject {

    public DiscogRelease(JSONObject json) {
        super(json.toMap());

    }

    public String getTitle() {
        return this.getString("title");
    }

    public String getArtist() {
        JSONArray artistsArray = (JSONArray) this.get("artists");
        int length = artistsArray.length();
        String[] artists = new String[length];
        for (int i = 0; i < length; i++) {
            JSONObject artist = artistsArray.getJSONObject(i);
            artists[i] = (String) artist.get("name");
        }
        return String.join(" & ", artists);
    }

    /**
     * @see https://de.wikipedia.org/wiki/Liste_der_ID3v1-Genres
     * @return corresponding number of genre
     */
    public int getGenre() {
        JSONArray genresArray = (JSONArray) this.get("genres");
        int length = genresArray.length();

        if (length == 0) {
            return 0;
        }
        ID3Genres id3Genres = new ID3Genres();
        return id3Genres.get(genresArray.getString(0));
    }

    public String getGenreDescription() {
        JSONArray genresArray = (JSONArray) this.get("genres");
        int length = genresArray.length();
        String[] genres = new String[length];
        for (int i = 0; i < length; i++) {
            genres[i] = (String) genresArray.getString(i);
        }
        return String.join(" / ", genres);
    }

    public ID3v24Tag toID3() {
        ID3v24Tag id3 = new ID3v24Tag();

        id3.setTitle(getTitle());
        id3.setArtist(getArtist());
        id3.setGenreDescription(getGenreDescription());
        id3.setGenre(getGenre());

        return id3;
    }

}
