package bll.movieLogic;
import be.Category;
import be.Movie;
import dal.IMovieDao;
import dal.MovieDao;
import exceptions.MoviesException;
import utility.MovieFormat;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class MovieCreation {
    private final IMovieDao movieDao;

    public MovieCreation() {
        this.movieDao = new MovieDao();
    }

    /**
     * extract format off the movie, in order to know the file format
     */
    public MovieFormat extractFormat(String name) throws MoviesException {
        MovieFormat movieFormat = null;
        int index = name.lastIndexOf('.');
        String format = "";
        if (index > 0 && index < name.length() - 1) {
            format = name.substring(index + 1);
        }
        MovieFormat[] movieFormats = MovieFormat.values();
        for (MovieFormat elem : movieFormats) {
            if (elem.getValue().equalsIgnoreCase(format)) {
                movieFormat = elem;
            }
        }
        if (movieFormat == null) {
            throw new MoviesException("Format not supported.Supported files:MP4,MPEG4!");
        }
        return movieFormat;
    }
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

    public boolean updateMovie(Movie movie) throws MoviesException {
        return this.movieDao.updateMovie(movie);
    }

    public boolean saveMovie(Movie movie, List<Category> categories) throws MoviesException {
        return this.movieDao.createMovie(movie,categories);
    }

    public boolean deleteMovie(Movie movie) throws MoviesException {
        return this.movieDao.deleteMovieFromLocalAndDB(movie);
    }

    public boolean validateRatingValue(double value) {
        return value>=0 && value<=10;
    }

    public boolean compareCurrentDateWithMovieLastViewDate(Date lastView) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastViewConverted = Instant.ofEpochMilli(lastView.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return currentDate.equals(lastViewConverted);
    }

    public void saveCurrentViewDate(int movieId) throws MoviesException {
        movieDao.updateCurrentViewDate(movieId);
    }
}

