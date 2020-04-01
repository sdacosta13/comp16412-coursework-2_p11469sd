package maze.routing;
import java.util.Stack;
import maze.*;
import java.util.List;
public class RouteFinder{
  private Maze maze;
  private Stack<Tile> route;
  private boolean finished;
  public RouteFinder(Maze maze){
    this.maze = maze;
  }
  public Maze getMaze(){
    return this.maze;
  }
  public List<Tile> getRoute(){
    ;
  }
  public boolean isFinished(){
    return this.finished;
  }
  public static RouteFinder load(String path){
    ;
  }
}
