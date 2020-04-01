package maze.routing;
import java.util.Stack;
import maze.*;
/*
import maze.BoolArray;
import maze.Coordinate;
import maze.Direction;
import maze.InvalidMazeException;
import maze.Maze.java;
import maze.MultipleEntranceException;
*/
import java.util.List;
public class RouteFinder{

  private Maze maze;
  private Stack<Tile> route;
  private boolean finished;
  private BoolArray bMap;
  public final Direction[] dirs = {Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
  public RouteFinder(Maze maze){
    this.maze = maze;
    this.bMap = new BoolArray(this.maze.getDimensions());
    route.push(this.maze.getEntrance());

  }
  public Boolean step(){
    Tile initial = route.pop();
    route.push(initial);
    Tile next;
    boolean dead = true;
    for (int i = 0; i < 4; i++){
      next = this.maze.getAdjacentTile(initial, dirs[i]);
      if(next.getType() != Type.WALL && !this.route.contains(next) && !this.bMap.isVisited(next.getCoords())){
        route.push(next);
        this.bMap.visit(next.getCoords());
        dead = false;
      }
    }
    if(dead){
      Tile toDie;
      toDie = route.pop();
    }
    return null;
  }
  public Maze getMaze(){
    return this.maze;
  }
  public List<Tile> getRoute(){
    return this.route;
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
    //Draw bMap first
    //Overwrite with Maze map
    //Overwrite with path
    char[] returnString = bMap.toString().toCharArray();
    for(int y = 0; y < this.maze.getDimensions()[1]; y++){
      for(int x = 0; x < this.maze.getDimensions()[0]; x++){
        String s = this.maze.getTileAtLocation(new Coordinate(x,y)).toString();
        //account for \n
        int pos = y*(this.maze.getDimensions()[0]+1)+x;
        returnString[pos] = s.charAt(0);
      }
    }
    return returnString.toString();
  }
}
