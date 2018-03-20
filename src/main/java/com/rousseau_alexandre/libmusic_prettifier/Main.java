package com.rousseau_alexandre.libmusic_prettifier;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedTagException, InvalidDataException {
        String directory = "/home/arousseau/Musique/Inconnu/Inconnu/";
        File dir = new File(directory);
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".mp3"));

        for (File file : files) {
            MyMp3File mp3file = new MyMp3File(file.getAbsolutePath());

            for (MissingTag missing : mp3file.getMissingTags()) {
                System.err.println(missing);
            }
        }
    }

}
