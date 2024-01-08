package bll;

import be.Category;
import be.Movie;
import dal.CategoryDao;
import dal.ICategoryDao;
import exceptions.MoviesException;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryLogic implements CategoryLogicAPI{
    private final ICategoryDao categoryDao;
    private static CategoryLogic instance;

    public CategoryLogic() {
        this.categoryDao = new CategoryDao();
    }

    @Override
    public List<Category> getAllCategories() throws MoviesException {
        Map<Integer,Category> categoryMap = categoryDao.getCategories();
        return categoryMap.keySet().stream().map(categoryMap::get).collect(Collectors.toList());
    }

    @Override
    public Movie applyFilter(String filter, ObservableList<Movie> movies) {
        return null;
    }

    public boolean createCategory(Category category) throws MoviesException {
        return this.categoryDao.createCategory(category);
    }

    public static CategoryLogic getInstance() throws MoviesException {
        if (instance == null){
            instance = new CategoryLogic();
        }
        return instance;

    }

}
