package bll.genreLogic;

import be.Genre;
import dal.GenreDao;
import dal.IGenreDao;
import exceptions.MoviesException;

import java.util.List;

public class GenreLogic implements GenreLogicApi {
    private final IGenreDao genreDao;

    public GenreLogic() {
        this.genreDao = new GenreDao();
    }

    @Override
    public List<String> getGenres() throws MoviesException {
        return genreDao.retrieveGenres().stream().map(Genre::getName).toList();
    }


}
