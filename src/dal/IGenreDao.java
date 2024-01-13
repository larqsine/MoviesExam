package dal;

import be.Genre;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface IGenreDao {
    Map<String,Genre> retrieveGenres() throws MoviesException;

}
