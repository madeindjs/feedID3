package com.rousseau_alexandre.libmusicPrettifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Wrapper for Discog API
 *
 * @see https://www.discogs.com/developers
 */
public class Discog {

    public static final String CONSUMER_KEY = "xEnspHdzbJFKuGlvlNde";
    public static final String CONSUMER_SECRET = "jtgMHnbpRaGbXEDBXimCDddFqLtzZTAG";

    /**
     * Send a GET request to find something
     *
     * @see
     * https://www.discogs.com/developers/#page:database,header:database-search
     *
     * @param what
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public JSONObject search(String what) throws MalformedURLException, IOException {
        String targetURL = String.format(
                "https://api.discogs.com/database/search?q=%s&key=%s&secret=%s",
                URLEncoder.encode(what),
                CONSUMER_KEY,
                CONSUMER_SECRET
        );
        JSONArray results = (JSONArray) getJsonFromUrl(targetURL).get("results");

        if (results.length() == 0) {
            return null;
        }

        JSONObject firstResult = (JSONObject) results.get(0);
        String ressourceUrl = (String) firstResult.get("resource_url");

        return (JSONObject) getJsonFromUrl(ressourceUrl);

    }

    private JSONObject getJsonFromUrl(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);

        StringBuilder response = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }
        return new JSONObject(response.toString());
    }

}
