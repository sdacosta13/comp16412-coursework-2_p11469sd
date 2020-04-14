package maze;
import java.util.ArrayList;
import maze.*;
import java.io.*;
/**
 * Class used to note where the routing algorithm has searched
 */
public class BoolArray implements Serializable{
  /**
   * array is used to store a duplicate map noting whether the Tile has been visited or not
   */
  private ArrayList<ArrayList<Boolean>> array;
  /**
   * Constructer for boolarray
   * @param dimensions - in the form [x,y]
   */
  public BoolArray(int[] dimensions){
    ArrayList<ArrayList<Boolean>> newArray = new ArrayList<ArrayList<Boolean>>();
    for(int i = 0; i < dimensions[1]; i++){
      newArray.add(new ArrayList<Boolean>());
      for(int j = 0; j < dimensions[0]; j++){
        newArray.get(i).add(false);
      }
    }
    this.array = newArray;
  }
  /**
   * @return the boolArray
   */
  public ArrayList<ArrayList<Boolean>> get(){
    return this.array;
  }
  /**
   * @param c - the coordinate of the target tile
   * @return wether the target has been visited or not
   */
  public Boolean isVisited(Maze.Coordinate c){
    if (c.getX() < 0 || c.getY() < 0 || c.getX() >= this.xSize() || c.getY() >= this.ySize()){
      return true;
    } else {
      return this.array.get(c.getY()).get(c.getX());
    }
  }
  /**
   * @return the y dimension of the array
   */
  public int ySize(){
    return this.array.size();
  }
  /**
   * @return the x dimension of the array
   */
  public int xSize(){
    return this.array.get(0).size();
  }
  /**
   * @param c - the coordinate of the tile to be set to visited
   */
  public void visit(Maze.Coordinate c){
    this.array.get(c.getY()).set(c.getX(), true);
  }
  /**
   * This method returns a bool array based on the maze take as parameter
   * @param m - the maze to generate a bMap for
   * @return a maze where all of the corresponding wall tiles have been set to visited
   */
  public static BoolArray ScanMaze(Maze m){
    Maze.Coordinate target;
    BoolArray newArray = new BoolArray(m.getDimensions());
    for(int y = 0; y < newArray.ySize(); y++){
      for(int x = 0; x < newArray.xSize(); x++){
        target = new Maze.Coordinate(x,y);
        if(m.getTileAtLocation(target).getType() == Tile.Type.WALL){
          newArray.visit(target);
        }
      }
    }
    return newArray;
  }
  /**
   * This method returns a string representaion much like that of Maze.toString()
   * @return a text representaion of the bool map
   */
  public String toString(){
    String returnString = "";
    for(int y = 0; y < this.ySize(); y++){
      for(int x = 0; x < this.xSize(); x++){
        if(this.isVisited(new Maze.Coordinate(x,y))){
          returnString += "1";
        } else {
          returnString += "0";
        }
      }
      returnString += "\n";
    }
    return returnString;
  }

}
