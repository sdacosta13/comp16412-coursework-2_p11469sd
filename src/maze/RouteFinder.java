package maze;
import java.util.Stack;
public class RouteFinder{
  private Maze maze;
  private Stack<Tile> route;
  private boolean finished;
  public RouteFinder(Maze maze){
    this.maze = maze;
  }
}
