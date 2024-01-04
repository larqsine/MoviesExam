package dal;

import exceptions.MoviesException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MovieDao {
    private final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    private Map<String,String> getMovies(){
        Map<String,String> movies = new HashMap<>();
        try (Connection conn = CONNECTION_MANAGER.getConnection()) {
            String sql = "SELECT m.id,m.name, m.rating, m.filelink, m.lastview, m.personalRating, c.id, c.name" +
                    " FROM CatMovies cm" +
                    " JOIN Movies m  on m.id = cm.MovieId" +
                    " LEFT JOIN Category c on c.id = cm.CategoryId";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int pId = rs.getInt(1);
                String pName = rs.getString(2);
                int sId = rs.getInt(3);
                String sPath = rs.getString(4);
                String sTitle = rs.getString(5);
                String sArtist = rs.getString(6);
                String sGenre = rs.getString(7);
                Long sLength = rs.getLong(8);
                playlist = new PlayList(pId, pName, null);
                song = new Song(sId, sPath, sTitle, sArtist, sGenre, sLength);

                if (playlistMap.containsKey(playlist.getId())) {
                    playlistMap.get(playlist.getId()).getPlayListSongs().add(song);
                } else {
                    playlist.getPlayListSongs().add(song);
                    playlistMap.put(playlist.getId(), playlist);
                }
            }
        } catch (SQLException | MoviesException e) {
            throw new MyTunesException(ExceptionsMessages.READING_FROMDB_FAILED);
        }
        playlistMap.keySet().forEach(elem -> playLists.add(playlistMap.get(elem)));
        this.allPlaylists = playLists;
        return movies;
    }

}
