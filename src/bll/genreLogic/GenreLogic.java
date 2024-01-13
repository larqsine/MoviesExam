package bll.genreLogic;

import be.Genre;
import dal.GenreDao;
import dal.IGenreDao;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public class GenreLogic implements GenreLogicApi {
    private final IGenreDao genreDao;

    public GenreLogic() {
        this.genreDao = new GenreDao();
    }

    @Override
    public Map<String, Genre> getGenres() throws MoviesException {
        return genreDao.retrieveGenres();
    }

    @Override
    public List<String> getGenreValues(Map<String, Genre> genreObjects) {
        return genreObjects.keySet().stream()
                .map(genreObjects::get)
                .map(Genre::getName)
                .toList();
    }

    @Override
    public List<Genre> convertStringsToGenre(Map<String, Genre> genreObjects, List<String> genres) {
        return genres.stream()
                .map(genreObjects::get)
                .toList();
    }


}
