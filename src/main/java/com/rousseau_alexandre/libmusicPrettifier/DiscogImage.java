/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rousseau_alexandre.libmusicPrettifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author apprenant
 */
public class DiscogImage {

    public final String mimetype;
    public final byte[] data;

    public DiscogImage(String url) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String mimetype = "";

        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            URL urlParsed = new URL(url);

            URLConnection connection = urlParsed.openConnection();
            connection.connect();
            mimetype = connection.getContentType();
            InputStream stream = connection.getInputStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mimetype = mimetype;
        this.data = outputStream.toByteArray();
    }

}
