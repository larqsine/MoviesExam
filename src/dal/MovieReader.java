package dal;

import exceptions.MoviesException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import utility.ExceptionsMessages;
import java.io.File;
import java.net.URI;

public class MovieReader implements IMovieReader {
    private Media readMedia(String path) throws MoviesException {
        URI mediaUri;
        try {
            mediaUri = new File(path).toURI();
            return new Media(mediaUri.toString());
        } catch (NullPointerException | MediaException | IllegalArgumentException | UnsupportedOperationException e) {
            throw new MoviesException(ExceptionsMessages.READING_MOVIE_LOCAL, e.getCause());
        }
    }

    @Override
    public Media getMedia(String path) throws MoviesException {
        return this.readMedia(path);
    }

    @Override
    public Media getInitialMedia() throws MoviesException {
        String path= "D:\\computer_science\\sco\\MediPlayer\\MoviesExam\\data\\default.mp4";
        Media media= getMedia(path);
        System.out.println(media);
        return media;
    }




}
