package com.rever.rever_b2b.downloader;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Matheswari on 4/5/2016.
 */
public class FileDownloader {

    public static void DownloadFile(String fileURL, File directory) {
        try {
            Log.i("myLog","DownloadFile");
            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
          //  c.setRequestMethod("GET");
           // c.setDoOutput(true);
            c.connect();
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                Log.i("while","while");
                f.write(buffer, 0, len1);
            }
            f.close();
            Log.i("myLog", "DownloadFile end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
