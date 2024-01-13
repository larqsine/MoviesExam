package bll.genreLogic;

import be.Genre;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface GenreLogicApi {
    Map<String,Genre> getGenres() throws MoviesException;
   List< String> getGenreValues(Map<String,Genre> genreObjects);
    List<Genre> convertStringsToGenre(Map<String,Genre> genreObjects,List<String> genres);
}
