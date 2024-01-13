package bll.movieLogic;
import exceptions.MoviesException;
import utility.MovieFormat;

import java.io.File;

public class MovieCreation {
    /**
     * extract format off the song, in order to know the file format
     */
    public MovieFormat extractFormat(String name) throws MoviesException {
        MovieFormat songFormat = null;
        int index = name.lastIndexOf('.');
        String format = "";
        if (index > 0 && index < name.length() - 1) {
            format = name.substring(index + 1);
        }
        MovieFormat[] movieFormats = MovieFormat.values();
        for (MovieFormat elem : movieFormats) {
            if (elem.getValue().equalsIgnoreCase(format)) {
                songFormat = elem;
            }
        }
        if (songFormat == null) {
            throw new MoviesException("Format not supported.Supported files:MP4,MPEG4!");
        }
        return songFormat;}
    /**
     * checks if title or path are empty*/
    public boolean areTitleOrPathEmpty(String title,String path){
        return title.isEmpty()|| path.isEmpty();
    }
/**
 * check if file exists*/
    public boolean checkFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }



}
