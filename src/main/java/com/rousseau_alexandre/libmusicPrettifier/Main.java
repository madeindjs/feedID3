package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException {
        Librairy librairy = new Librairy("/home/arousseau/Musique/Inconnu/Inconnu/");
        Discog api = new Discog();
        JSONObject json = api.search("Red Pill");

        System.out.println(json);
    }

}
