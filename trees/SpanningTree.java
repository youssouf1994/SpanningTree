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
			// Let v be the representative of the connected component that contains e.
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

	public static Collection<Edge> primTree(HashSet<Place> nonVisited, Place start, EuclideanGraph g) {
		// Set of tree's edges.
		Collection<Edge> edges = new LinkedList<Edge>();
		// Queue on edges using an EdgeComparator.
		Queue<Edge> qEdges = new PriorityQueue<Edge>(1, new EdgeComparator());
		// Add all edges out from start to the queue.
		for(Edge e : g.edgesOut(start)) {
			qEdges.offer(e);
		}
		// Remove start from non visited set.
		nonVisited.remove(start);
		// While there is an edge in the priority queue.
		while(!qEdges.isEmpty()) {
			// Let e be the lowest edges in the priority queue.
			Edge e = qEdges.poll();
			// Let v be the targuet of e.
			Place v = e.target;
			// Check if v is not already visited.
			if(nonVisited.contains(v)) {
				// Add e to the tree.
				edges.add(e);
				// Add all edges out from v to the priority queue.
				for(Edge ee : g.edgesOut(v)) {
					qEdges.offer(ee);
				}
				// Remove v from the non visited set.
				nonVisited.remove(v);
			}
		}
		return edges;
	}

	public static Collection<Collection<Edge>> primForest(EuclideanGraph g) {
		
		Collection<Collection<Edge>> edges = new LinkedList<Collection<Edge>>();
		// Create a set of all non visited vertices.
		HashSet<Place> nonVisited = new HashSet<Place>();
		// Add all vertices to the non visited set.
		for(Place p : g.places()) {
			nonVisited.add(p);
		}
		// While there is a non visited vertex.
		while(!nonVisited.isEmpty()) {
			// Let start be a non visired vertex.
			Place start = nonVisited.iterator().next();
			// Get the tree of the connected component thet contains start.
			Collection<Edge> tEdges = primTree(nonVisited, start, g);
			// Check if the tree contain at least one vertex.
			if(!tEdges.isEmpty())
				// Add the tree to the forest.
				edges.add(tEdges);
		}
		return edges;
	}
}
