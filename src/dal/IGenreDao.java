package dal;

import be.Category;
import be.Genre;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface IGenreDao {
    Map<String,Genre> retrieveGenres() throws MoviesException;

    Map<String, Category>retrieveCategories() throws MoviesException;
}
