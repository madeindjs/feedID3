package com.rousseau_alexandre.libmusic_prettifier;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException {
        String filePath = "/home/arousseau/Musique/Inconnu/Inconnu/Inconnu - B Lewis - Strange Things.mp3.mp3";
        MyMp3File file = new MyMp3File(filePath);

        for (MissingTag missing : file.getMissingTags()) {
            System.err.println(missing);
        }
    }

}
