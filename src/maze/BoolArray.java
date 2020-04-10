/* Class used to note where the routing algorithm has searched*/

package maze;
import java.util.ArrayList;
import maze.*;
import java.io.*;
public class BoolArray implements Serializable{
  private ArrayList<ArrayList<Boolean>> array;
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
  public ArrayList<ArrayList<Boolean>> get(){
    return this.array;
  }
  public Boolean isVisited(Maze.Coordinate c){
    if (c.getX() < 0 || c.getY() < 0 || c.getX() >= this.xSize() || c.getY() >= this.ySize()){
      return true;
    } else {
      return this.array.get(c.getY()).get(c.getX());
    }
  }
  public int ySize(){
    return this.array.size();
  }
  public int xSize(){
    return this.array.get(0).size();
  }
  public void visit(Maze.Coordinate c){
    this.array.get(c.getY()).set(c.getX(), true);
  }
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
