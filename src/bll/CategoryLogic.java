package bll;

import be.Category;
import dal.CategoryDao;
import dal.ICategoryDao;
import exceptions.MoviesException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryLogic implements CategoryLogicAPI {
    private final ICategoryDao categoryDao;

    public CategoryLogic() {
        this.categoryDao = new CategoryDao();
    }

    @Override
    public List<Category> getAllCategories() throws MoviesException {
        Map<Integer,Category> categoryMap = categoryDao.getCategories();
        return categoryMap.keySet().stream().map(categoryMap::get).collect(Collectors.toList());
    }


}
