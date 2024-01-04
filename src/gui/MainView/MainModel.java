package gui.MainView;

import be.Category;
import bll.CategoryLogic;
import bll.CategoryLogicAPI;
import exceptions.MoviesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class MainModel {
    private final CategoryLogicAPI categoryLogic;
    private static MainModel instance;
    private final ObservableList<Category> categories;



    private MainModel() throws MoviesException {
        this.categoryLogic=new CategoryLogic();
        this.categories = FXCollections.observableArrayList();
        initializeCategories();
    }

    public static MainModel getInstance() throws MoviesException {
        if(instance==null){
            instance = new MainModel();
        }
        return instance;
    }

    private void initializeCategories() throws MoviesException {
        this.categories.setAll(this.categoryLogic.getAllCategories());
    }


    public ObservableList<Category> getCategories() {
        return categories;
    }










}
