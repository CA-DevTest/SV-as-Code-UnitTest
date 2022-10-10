package com.ca.devtest.sv.devtools.utils;

import java.io.*;

public class FileUtils {
    /***
     * Creates temp file with given file name and writes contents to it
     *
     * @param fileName - Name of the file
     * @param content - Contents to be written to the file
     * @return
     * @throws IOException
     */
    public static File crateTempFile (String fileName, String content) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File tempFile = new File(tempDir,fileName);
        BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
        try {
            out.write(content);
        }
        finally{
            out.close();
        }
        return tempFile;
    }
}
