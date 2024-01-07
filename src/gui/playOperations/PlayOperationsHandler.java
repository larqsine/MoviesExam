package gui.playOperations;

import exceptions.MoviesException;
import gui.MainView.MainModel;
import javafx.scene.media.Media;
import utility.ExceptionHandler;
import utility.Operations;

public class PlayOperationsHandler implements PlayOperations {
    private static PlayOperationsHandler instance;
    private MainModel model;

    private PlayOperationsHandler(MainModel model) {
        this.model = model;
    }

    public static PlayOperationsHandler getInstance(MainModel model) {
        if (instance == null) {
            instance = new PlayOperationsHandler(model);
        }
        return instance;
    }

    private Media performGetMedia(Operations operation) {
        Media media = null;
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 5) {
            try {
                media = getOperation(operation);
                retry = false;
            } catch (MoviesException e) {
                ExceptionHandler.displayErrorAlert(e.getMessage(), "Media reading error");
            }
            counter++;
        }
        return media;
    }

    private Media getOperation(Operations operation) throws MoviesException {
        Media media = null;
        switch (operation) {
            case Operations.GET_DEFAULT:
                media = model.getInitialDefaultMedia();
                break;
            case Operations.GET_NEXT:
                media = model.getNextMovie();
                break;
            case Operations.GET_PREVIOUS:
                media = model.getPreviousMovie();
                break;
            default:
                media = model.getCurrentMovieToBePlayed();
                break;
        }
        return media;
    }

    @Override
    public Media getMedia(Operations operation) {
        return performGetMedia(operation);
    }
}
