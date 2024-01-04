package dal;

import java.util.HashMap;
import java.util.Map;

public class MovieDao {
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    private Map<String,String> getMovies(){
        Map<String,String> movies = new HashMap<>();
        String sql = "Select * from CatMovie cm  ";
        return movies;
    }

}
