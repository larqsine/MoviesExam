package dal;
import exceptions.MoviesException;
import javafx.scene.media.Media;

public interface IMovieReader {
    public Media getMedia(String path) throws MoviesException;
}
