package dal;

import exceptions.MoviesException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    private static FileHandler instance;

    private FileHandler() {

    }

    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }


    private boolean deleteSongFromLocal(String path) throws MoviesException {

        try {
             return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            throw new MoviesException("Deleting movie from local gone wrong");
        }

    }

    public boolean deleteSongLocal(String path) throws MoviesException {
        return deleteSongFromLocal(path);
    }


}
