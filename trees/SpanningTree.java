package trees;

import java.util.*;

import graph.*;

public class SpanningTree {

    public static Collection<Edge> kruskal(UnionFind u, EuclideanGraph g){
      Collection<Edge> rEdges = new LinkedList<Edge>();
    	List<Edge> edges = g.getAllEdges();
      Collections.sort(edges, new EdgeComparator());
      for(Place p : g.places()) {
        u.find(p);
      }
      for(Edge e : edges) {
        if(u.find(e.source) != u.find(e.target)) {
          rEdges.add(e);
          u.union(e.source, e.target);
        }
      }
    	return rEdges;
    }

    public static Collection<Collection<Edge>> kruskal(EuclideanGraph g){
    	HashMap<Place, Collection<Edge>> edgelist = new HashMap<Place, Collection<Edge>>();
      UnionFind u = new UnionFind();
      Collection<Edge> edges = kruskal(u, g);
      for(Edge e : edges) {
        Place v = u.find(e.source);
        Place w = u.find(e.target);
        assert(v.equals(w));
        Collection<Edge> c = edgelist.get(v);
        if(c == null) {
          edgelist.put(v, new LinkedList<Edge>());
        }
        c = edgelist.get(v);
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
