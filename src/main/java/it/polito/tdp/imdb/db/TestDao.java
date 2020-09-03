package it.polito.tdp.imdb.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.imdb.model.Director;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		Map<Integer, Director> direttori = new HashMap<Integer,Director>();
		ImdbDAO dao = new ImdbDAO();
		System.out.println("Actors:");
		System.out.println(dao.listAllActors());
		System.out.println("Movies:");
		System.out.println(dao.listAllMovies());
		System.out.println("Directors:");
		System.out.println(dao.listAllDirectors());
		System.out.println("Creato grafo con:");
		System.out.println("#Vertici: "+dao.getVertici(2005, direttori).size());
		System.out.println("#Archi: "+dao.getArchi(direttori).size());
		
	}

}
