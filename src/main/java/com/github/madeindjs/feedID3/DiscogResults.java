package com.github.madeindjs.feedID3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author arousseau
 */
public class DiscogResults implements Iterable<DiscogRelease> {

    private final JSONArray results;
    private final int length;
    private int cursor = 0;

    public DiscogResults(JSONObject response) {
        this(response.getJSONArray("results"));
    }

    public DiscogResults(JSONArray results) {
        this.results = results;
        this.length = results.length();
    }

    public int getLength() {
        return length;
    }

    @Override
    public Iterator<DiscogRelease> iterator() {
        return new Iterator<DiscogRelease>() {
            @Override
            public boolean hasNext() {
                return DiscogResults.this.cursor < DiscogResults.this.length;
            }

            @Override
            public DiscogRelease next() {
                JSONObject firstResult = (JSONObject) results.get(DiscogResults.this.cursor);
                String ressourceUrl = (String) firstResult.get("resource_url");

                DiscogResults.this.cursor++;

                try {
                    return new DiscogRelease((JSONObject) getJsonFromUrl(ressourceUrl));
                } catch (IOException ex) {
                    Logger.getLogger(DiscogResults.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }
        };
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
