package bll;

import be.Category;
import exceptions.MoviesException;

import java.util.List;

public interface CategoryLogicAPI {
    List<Category> getAllCategories() throws MoviesException;
}
