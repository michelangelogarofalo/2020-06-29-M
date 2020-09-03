package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(/*Map<Integer,Director> directorMap*/){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				//directorMap.put(res.getInt("id"), director);
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> getVertici(int x,Map<Integer,Director> directorMap){
		String sql = "SELECT  DISTINCT(d.id) , d.first_name  , d.last_name \n" + 
				"FROM directors AS d , movies_directors AS md , movies AS m\n" + 
				"WHERE d.id=md.director_id\n" + 
				"AND m.id= md.movie_id\n" + 
				"AND m.year=?";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!directorMap.containsKey(res.getInt("id"))) {
					Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
					directorMap.put(res.getInt("id"), director);
					result.add(director);					
				}				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(Map<Integer,Director> directorMap){
		String sql = "SELECT COUNT(DISTINCT r.actor_id) AS X , md.director_id AS id1 , md2.director_id AS id2\n" + 
				"FROM roles AS r, movies_directors AS md , movies_directors AS md2 , roles AS r1\n" + 
				"WHERE md.director_id > md2.director_id\n" + 
				"AND md.movie_id = r.movie_id\n" + 
				"AND md2.movie_id = r1.movie_id \n" + 
				"AND r.actor_id=r1.actor_id\n" + 
				"GROUP BY md.director_id , md2.director_id";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(directorMap.containsKey(res.getInt("id1")) && directorMap.containsKey(res.getInt("id2")) ) {
					Adiacenza a = new Adiacenza(directorMap.get(res.getInt("id1")),directorMap.get(res.getInt("id2")),res.getInt("X"));
					result.add(a);					
				}				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
