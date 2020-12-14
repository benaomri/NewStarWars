package bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * public class that take the data from the json input and init 'inputApp'
 */
public class JsonInputReader {
    public static InputApp getInputFromJson(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, InputApp.class);
        }
    }
}
