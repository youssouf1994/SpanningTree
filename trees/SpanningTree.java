package trees;

import java.util.*;

import graph.*;

public class SpanningTree {

	public static Collection<Edge> kruskal(UnionFind u, EuclideanGraph g){
		// Set of forest's edges.
		Collection<Edge> rEdges = new LinkedList<Edge>();
		// Get the set of edges of the graph g.
		List<Edge> edges = g.getAllEdges();
		// Sort the set of edge by using an EdgeComparator.
		Collections.sort(edges, new EdgeComparator());
		for(Edge e : edges) {
			// Check if the source and the target of e are not already connected.
			if(!(u.find(e.source).equals(u.find(e.target)))) {
				// Add e to the set of forest's edges.
				rEdges.add(e);
				// Connect the source and the target of e.
				u.union(e.source, e.target);
			}
		}
		return rEdges;
	}

	public static Collection<Collection<Edge>> kruskal(EuclideanGraph g) {
		// Create an UnionFind instance.
		UnionFind u = new UnionFind();
		// For every connected component, map its representative to its set of tree's edges. 
		HashMap<Place, Collection<Edge>> edgelist = new HashMap<Place, Collection<Edge>>();
		// Get the set of forest's edges.
		Collection<Edge> edges = kruskal(u, g);
		for(Edge e : edges) {
			// Get the representative of the connected component that contain e.
			Place v = u.find(e.source);
			// Get the tree correspond to the representative v.
			Collection<Edge> c = edgelist.get(v);
			// Check if the tree was not  already added.
			if(c == null) {
				edgelist.put(v, new LinkedList<Edge>());
			}
			c = edgelist.get(v);
			// Add e to the tree.
			c.add(e);
		}
		return edgelist.values();
	}

	public static Collection<Edge> primTree(HashSet<Place> nonVisited, Place start, EuclideanGraph g){
		Collection<Edge> edges = new LinkedList<Edge>();
		Queue<Edge> qEdges = new PriorityQueue<Edge>(g.getAllEdges().size(), new EdgeComparator());
		for(Edge e : g.edgesOut(start)) {
			qEdges.offer(e);
		}

		nonVisited.remove(start);

		while(!qEdges.isEmpty()) {
		Edge e = qEdges.poll();
		Place v = e.target;
			if(nonVisited.contains(v)) {
				edges.add(e);
				for(Edge ee : g.edgesOut(v)) {
				qEdges.offer(ee);
				}
				nonVisited.remove(v);
			}
		}
		return edges;
	}

	public static Collection<Collection<Edge>> primForest(EuclideanGraph g){
		Collection<Collection<Edge>> edges = new LinkedList<Collection<Edge>>();
		HashSet<Place> nonVisited = new HashSet<Place>();
		for(Place p : g.places()) {
			nonVisited.add(p);
		}
		Queue<Edge> qEdges = new PriorityQueue<Edge>(g.getAllEdges().size(), new EdgeComparator());
		while(!nonVisited.isEmpty()) {
			Collection<Edge> tEdges = new LinkedList<Edge>();
			Place start = nonVisited.iterator().next();
			for(Edge e : g.edgesOut(start)) {
				qEdges.offer(e);
			}
			nonVisited.remove(start);
			while(!qEdges.isEmpty()) {
				Edge e = qEdges.poll();
				Place v = e.target;
				if(nonVisited.contains(v)) {
					tEdges.add(e);
					for(Edge ee : g.edgesOut(v)) {
						qEdges.offer(ee);
					}
					nonVisited.remove(v);
				}
			}
			if(!tEdges.isEmpty())
				edges.add(tEdges);
		}
		return edges;
	}
}
