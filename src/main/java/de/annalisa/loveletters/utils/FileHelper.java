package de.annalisa.loveletters.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * The FileHelper class provides utility methods for reading and retrieving the contents of a file.
 */
public class FileHelper {
    /**
     * Reads and retrieves the contents of a file from the resources directory.
     *
     * @param filePath The path to the file within the resources directory.
     * @return The contents of the file as a string, or null if an error occurred during file retrieval.
     */
    public static String readFileFromResources(String filePath){
        try {
            var stream = ClassLoader.getSystemResourceAsStream(filePath);
            StringBuilder stringBuilder = new StringBuilder();

            assert stream != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String ruleLine;
            while ((ruleLine = br.readLine())!= null){
                stringBuilder.append(ruleLine).append('\n');
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e){
            System.out.println("Something went wrong while retrieving the rules... please google them instead.");
        } catch (Exception e) {
            System.out.println("Oh no.. something went wrong :(\n" + e.getMessage());
        }
        return null;
    }
}
