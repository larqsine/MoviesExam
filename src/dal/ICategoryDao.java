package dal;

import be.Category;
import exceptions.MoviesException;

import java.util.List;
import java.util.Map;

public interface ICategoryDao {
    Map<Integer,Category> getCategories() throws MoviesException;

    boolean createCategory(Category category);
}
