package bll.genreLogic;

import be.Category;
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

    @Override
    public List<Category> convertStringsToCategory(Map<String, Category> categoryMapObjects, List<String> genres) {
        return genres.stream()
                .map(categoryMapObjects::get)
                .toList();
    }

    @Override
    public List<String> getCategoriesValues(Map<String, Category> categoryObjects) {
        return categoryObjects.keySet().stream()
                .map(categoryObjects::get)
                .map(Category::getName)
                .toList();
    }

    @Override
    public Map<String, Category> getCategories() throws MoviesException {
        return genreDao.retrieveCategories();
    }


}
