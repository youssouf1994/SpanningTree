package trees;

import graph.Place;

import java.util.HashMap;
import java.util.Random;
// Q1

public class UnionFind {
  // Create a random number's generator.
  static Random random = new Random();

	//parent relation, parent.put(src,dst) indicates that src points to dst
  private HashMap<Place,Place> parent;

  public UnionFind(){
    // Initialization.
    parent = new HashMap<Place,Place>();
    /*for(Place place : places) {
      parent.put(place, place);
    }*/
  }

  public Place find( Place src ){
    // Take the parent of src
    Place par = parent.get(src);
    if(par == null) {
      parent.put(src, src);
      return src;
    }
    // Check if the src is its parent. In this case return src.
    if (par.equals(src)) {
      return src;
    }
    else {
      // Look for the representative of par.
      Place rep = find(par);
      // Path compression :
      if(!rep.equals(par)) {
        parent.put(src, rep);
      }
      return rep;
    }
  }

  public void union( Place v0, Place v1 ){
    // Look for the representative of v0 and v1.
    Place rep0 = find(v0);
    Place rep1 = find(v1);
    // Check if rep0 != rep1
    if(!rep0.equals(rep1)) {
      // Take a random boolean. If it is true put the rep0 as parent of rep1,
      // otherwise put rep1 as parent of rep0.
      if(random.nextBoolean()) {
        parent.put(rep1, rep0);
      }
      else {
        parent.put(rep0, rep1);
      }
    }
    return;
  }
}
