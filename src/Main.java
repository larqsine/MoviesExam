import be.Movie;
import dal.IMovieDao;
import dal.MovieDao;
import exceptions.MoviesException;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws MoviesException {
        IMovieDao  iMovieDao  = new MovieDao();
        iMovieDao.testConnection();
        iMovieDao.getMovies().forEach(System.out::println);


        }

}