import be.Movie;
import dal.CategoryDao;
import dal.ICategoryDao;
import dal.IMovieDao;
import dal.MovieDao;
import exceptions.MoviesException;

import java.util.List;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws MoviesException {
        IMovieDao  iMovieDao  = new MovieDao();
        ICategoryDao iCategoryDao = new CategoryDao();
        iMovieDao.testConnection();
        List<Movie> movies=iMovieDao.getMovies().keySet().stream().map((elem)-> {
           Movie movie;
            try {
                movie = iMovieDao.getMovies().get(elem);
            } catch (MoviesException e) {
                throw new RuntimeException(e);
            }
            return movie;
        }).toList();
    movies.forEach(System.out::println);
        System.out.println(movies.size());
      iCategoryDao.getCategories().forEach(System.out::println);



    }
}