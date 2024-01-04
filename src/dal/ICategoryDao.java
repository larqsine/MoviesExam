package dal;

import be.Category;
import exceptions.MoviesException;

import java.util.List;

public interface ICategoryDao {
    List<Category> getCategories() throws MoviesException;
}
