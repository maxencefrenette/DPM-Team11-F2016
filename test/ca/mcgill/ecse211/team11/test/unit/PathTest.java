package ca.mcgill.ecse211.team11.test.unit;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import ca.mcgill.ecse211.team11.pathfinding.Path;
import ca.mcgill.ecse211.team11.pathfinding.PathNode;

public class PathTest {

  @Test
  public void test() {
    PathNode node1 = new PathNode(0, 0, 0);
    PathNode node2 = new PathNode(3, 4, 0);

    Path p = new Path(node1);
    assertEquals("An empty path should have length 0", 0, p.length(), 0.001);

    p.addNode(node2);
    assertEquals("The path length is calculated properly", 5, p.length(), 0.001);

    Iterator<PathNode> it = p.iterator();
    assert (it.hasNext());
    assertEquals(node1, it.next());
    assert (it.hasNext());
    assertEquals(node2, it.next());
  }
}
