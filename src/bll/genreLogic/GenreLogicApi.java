package bll.genreLogic;

import be.Category;
import be.Genre;
import be.Movie;
import exceptions.MoviesException;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public interface GenreLogicApi {
    Map<String,Genre> getGenres() throws MoviesException;
   List<String> getGenreValues(Map<String,Genre> genreObjects);
    List<Genre> convertStringsToGenre(Map<String,Genre> genreObjects,List<String> genres);

    List<String> getCategoriesValues(Map<String, Category> categoryObjects);

    Map<String,Category> getCategories() throws MoviesException;
    List<Category> convertStringsToCategory(Map<String, Category> categoryMapObjects, List<String> genres);
}
