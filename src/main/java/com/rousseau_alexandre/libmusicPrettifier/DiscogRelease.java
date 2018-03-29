package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.ID3v24Tag;
import org.json.JSONArray;
import org.json.JSONException;
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
        try {
            return this.getString("title");
        } catch (JSONException e) {
            return null;
        }
    }

    public String getAlbum() {
        try {
            return this.getString("title");
        } catch (JSONException e) {
            return null;
        }
    }

    public int getYear() {
        try {
            return this.getInt("year");
        } catch (JSONException e) {
            return 0;
        }
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

    public String getArtistUrl() {
        try {
            JSONArray artistsArray = (JSONArray) this.get("artists");
            return artistsArray.getJSONObject(0).getString("resource_url");
        } catch (JSONException e) {
            return null;
        }
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
        final ID3Genres id3Genres = new ID3Genres();
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
        // artist
        id3.setArtist(getArtist());
        id3.setArtistUrl(getArtistUrl());
        id3.setAlbumArtist(getArtist());
        // genre
        id3.setGenreDescription(getGenreDescription());
        id3.setGenre(getGenre());

        id3.setYear(Integer.toString(getYear()));

        return id3;
    }

}
