package dal;

import be.Genre;
import exceptions.MoviesException;

import java.util.List;

public interface IGenreDao {
    List<Genre> retrieveGenres() throws MoviesException;

}
