package bll.genreLogic;

import be.Genre;
import exceptions.MoviesException;

import java.util.List;

public interface GenreLogicApi {
    List<String> getGenres() throws MoviesException;
}
