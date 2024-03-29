package bll.categoryLogic;

import be.Category;
import be.Movie;
import dal.CategoryDao;
import dal.ICategoryDao;
import exceptions.MoviesException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryLogic implements CategoryLogicAPI {
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

    public boolean createCategory(String  categoryTitle) throws MoviesException {
        return this.categoryDao.createCategory(categoryTitle);
    }

    @Override
    public boolean checkTitle(String title) {
        return title.isEmpty();
    }

    @Override
    public boolean updateCategory(int categoryId, String newTitle) throws MoviesException {
        return categoryDao.updateCategory(categoryId,newTitle);
    }

    @Override
    public boolean deleteCategory(int id) throws MoviesException {
        return this.categoryDao.deleteCategory(id);
    }


    public boolean checkOpenCategory(SimpleIntegerProperty openCategoryId, int currentCategoryToDelete) {
        return openCategoryId.get() == currentCategoryToDelete;
    }

    public static CategoryLogic getInstance() throws MoviesException {
        if (instance == null){
            instance = new CategoryLogic();
        }
        return instance;
    }


}
