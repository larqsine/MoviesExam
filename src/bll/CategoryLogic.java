package bll;

import be.Category;
import dal.CategoryDao;
import dal.ICategoryDao;
import exceptions.MoviesException;

import java.util.List;

public class CategoryLogic implements CategoryLogicAPI {
    private final ICategoryDao categoryDao;

    public CategoryLogic() {
        this.categoryDao = new CategoryDao();
    }

    @Override
    public List<Category> getAllCategories() throws MoviesException {
        return this.categoryDao.getCategories();
    }
}
