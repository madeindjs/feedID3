package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException {
        Librairy librairy = new Librairy("src/test/resources");
        librairy.correct();
    }

}
