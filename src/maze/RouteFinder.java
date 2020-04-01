package maze.routing;
import java.util.Stack;
import maze.*;
import java.util.List;
public class RouteFinder{

  private Maze maze;
  private Stack<Tile> route;
  private boolean finished;
  private BoolArray bMap;
  public Coordinate searchNode; 
  public RouteFinder(Maze maze){
    this.maze = maze;
    this.bMap = new BoolArray(this.maze.getDimensions());
  }
  public Boolean step(){

  }
  public Maze getMaze(){
    return this.maze;
  }
  public List<Tile> getRoute(){
    return null;
  }
  public boolean isFinished(){
    return this.finished;
  }
  public static RouteFinder load(String path){
    return null;
  }
  public void Save(String filename){
    ;
  }
  public String toString(){
    return null;
  }
}
