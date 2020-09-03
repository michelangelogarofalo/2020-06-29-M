package it.polito.tdp.imdb.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO DAO;
	private List <Integer> anno = new LinkedList<Integer>();
	private Map <Integer,Director> idMap = new HashMap <Integer,Director>();
	private Graph<Director, DefaultWeightedEdge> grafo;
	public Model() {
		DAO= new ImdbDAO();
	}
	
	public List<Integer> boxAnnoCrea() {		
		anno.add(2004);
		anno.add(2005);
		anno.add(2006);
		return anno;
		
	}
	public void creaGrafo(int x) {

		grafo= new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			
		Graphs.addAllVertices(grafo,DAO.getVertici(x, idMap));
		for(Adiacenza a : DAO.getArchi(idMap)) {
			if(grafo.containsVertex(a.getD1()) && grafo.containsVertex(a.getD2())) {
				Graphs.addEdgeWithVertices(grafo,a.getD1(),a.getD2(), a.getPeso());
			}
		}
		
		System.out.println("Creato grafo con:\n#VERTICI: "+grafo.vertexSet().size()+"#ARCHI: "+grafo.edgeSet().size());
		
	}

}
