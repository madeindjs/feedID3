package com.rousseau_alexandre.libmusicPrettifier;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent a folder containing MP3 files
 */
public class Librairy {

    private HashSet<MyMp3File> mp3Files = new HashSet();

    /**
     * @param path Path containing my folder
     */
    public Librairy(String path) {
        findMp3FilesInFolder(path);
    }

    public void correct() {
        for (MyMp3File mp3File : mp3Files) {
            mp3File.getInformations();
        }
    }

    /**
     * Recursive MP3 file search
     *
     * @see
     * https://stackoverflow.com/questions/2056221/recursively-list-files-in-java
     * @param path
     */
    private void findMp3FilesInFolder(String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        if (list == null) {
            return;
        }
        for (File file : list) {
            if (file.isDirectory()) {
                findMp3FilesInFolder(file.getAbsolutePath());
            } else if (file.isFile() && file.getName().endsWith(".mp3")) {
                try {
                    mp3Files.add(new MyMp3File(file.getAbsolutePath()));
                } catch (IOException ex) {
                    Logger.getLogger(Librairy.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedTagException ex) {
                    Logger.getLogger(Librairy.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidDataException ex) {
                    Logger.getLogger(Librairy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public HashSet<MyMp3File> getMp3Files() {
        return mp3Files;
    }

}
