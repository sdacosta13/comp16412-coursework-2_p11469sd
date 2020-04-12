package maze;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
/**
 * Stores and manipulates mazes
 */
public class Maze implements Serializable{
  /** Holds the tile containing the enterance to the maze */
  private Tile entrance = null;
  /** Holds the tile containing the exit to the maze */
  private Tile exit = null;
  /** Holds the array of tiles to represemt the maze */
  private List<List<Tile>> tiles;
  /** Holds the dimension of the maze in the form, [x ,y] */
  private int[] dimensions;
  /** Constructer used as a placeholder */
  private Maze(){
    ;
  }
  /**
   * Converts the tiles array to a printable string
   * @return a string representation of the maze, for the console
   */
  public String toString(){
    String returnString = "";
    for(int y = this.getDimensions()[1]-1; y >= 0; y--){
      for(int x = 0; x < this.getDimensions()[0]; x++){
        returnString += this.getTileAtLocation(new Coordinate(x,y)).toString();
      }
      returnString += '\n';
    }
    return returnString;
  }
  /**
   * @param t - the target base tile to get the adjacent tile from
   * @param d - the direction in which to check from the target tile
   * @return Tile adjacent to t, in the direction d
   */
  public Tile getAdjacentTile(Tile t, Direction d){
    Coordinate baseCoords = t.getCoords();
    int x = baseCoords.getX();
    int y = baseCoords.getY();
    switch (d){
      case NORTH:
        y += 1;
        break;
      case SOUTH:
        y -= 1;
        break;
      case EAST:
        x += 1;
        break;
      case WEST:
        x -= 1;
        break;
    }
    //if a tile out of range is referenced it may as well be a wall
    if(x < 0 || y < 0 || x >= this.getDimensions()[0] || y >= this.getDimensions()[1]){
      Tile wallTile = Tile.fromChar('#');
      wallTile.setCoords(new Coordinate(x,y));
      return wallTile;
    } else {
      return this.getTileAtLocation(new Coordinate(x,y));
    }
  }
  /**
   * @param path - takes the absolute path of the maze.txt file to be read
   * @return a maze object representing the txt file
   */
  public static Maze fromTxt(String path) throws InvalidMazeException{
    //Get dimensions first
    int entriesSeen = 0;
    int exitsSeen = 0;
    Maze newMaze = null;
    try {
      FileReader newFile = new FileReader(path);
      String newString = "";
      int nls = 0;
      int j;
      int x = 0;
      ArrayList<Integer> lens = new ArrayList<Integer>();
      while((j=newFile.read()) != -1){
        x += 1;
        char curChar = (char) j;
        if( curChar == '\n'){
          nls += 1;
          lens.add(x);
          x = 0;
        }

        newString += String.valueOf(curChar);

      }
      Boolean different = false;
      for(int i = 0; i < lens.size(); i++){
        if(lens.get(0) != lens.get(i)){
          different = true;
        }
      }
      if(different){
        throw new RaggedMazeException("Maze lines differ in size");
      }


      int posOfNewLine = newString.indexOf('\n');
      if (newString.charAt(newString.length()-1) == '\n'){
        nls -= 1;
        newString = newString.substring(0,newString.length()-1);
      }


      //-------------------------------------
      newMaze = new Maze();
      nls += 1;
      newMaze.setDimensions(posOfNewLine,nls);
      List<List<Tile>> newTiles = new ArrayList<List<Tile>>();
      for(int i = 0; i < nls; i++){
        newTiles.add(new ArrayList<Tile>());
      }
      int y = nls-1;
      x = 0;
      Maze.Coordinate entryLoc = null;
      Maze.Coordinate exitLoc = null;
      for(int i = 0; i <newString.length(); i++){
        char curChar = newString.charAt(i);

        if(curChar == 'e'){
          entriesSeen += 1;
          entryLoc = new Maze.Coordinate(x,y);
        } else if (curChar == 'x'){
          exitsSeen += 1;
          exitLoc = new Maze.Coordinate(x,y);
        }
        if(curChar != '\n'){
          Tile newTile = Tile.fromChar(curChar);
          newTile.setCoords(new Coordinate(x,y));
          newTiles.get(y).add(newTile);
          x+= 1;
        } else {
          y -= 1;
          x = 0;

        }



      }

      if(entriesSeen < 1){
        throw new NoEntranceException("No Entrance in " + path);
      } else if (entriesSeen > 1){
        throw new MultipleEntranceException("Multiple Entrances Found in " + path);
      } else if (exitsSeen < 1){
        throw new NoExitException("No Exit in " + path);
      } else if (exitsSeen > 1){
        throw new MultipleExitException("Multiple Exits Found in " + path);
      }


      newMaze.setTiles(newTiles);
      newMaze.setExit(newMaze.getTileAtLocation(exitLoc));
      newMaze.setEntrance(newMaze.getTileAtLocation(entryLoc));
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e){
      System.out.println("IOException");
    }
    return newMaze;
  }
  /**
   * @param t - the target tile which you want the coordinates to
   * @return the coordinate of tile t
   */
  public Coordinate getTileLocation(Tile t){
    for(int y = 0; y < this.getDimensions()[1]; y++){
      for(int x = 0; x < this.getDimensions()[0]; x++){
        if(this.getTileAtLocation(new Coordinate(x,y)) == t){
          return new Coordinate(x,y);
        }
      }
    }
    return null;
  }
  /**
   * Sets the enterance attribute
   * @param t - the entrance tile
   */
  private void setEntrance(Tile t) throws MultipleEntranceException{
    Boolean inTiles = false;
    for(int i = 0; i < this.dimensions[1]; i++){
      for(int j = 0; j < this.dimensions[0]; j++){
          if(this.getTileAtLocation(new Maze.Coordinate(j,i)) == t){
            inTiles = true;
          }
      }
    }
    if(inTiles){
      if(this.entrance == null){
        this.entrance = t;
      } else {
        throw new MultipleEntranceException("Multiple entrances found");
      }
    }

  }
  /**
   * Sets the exit attribute
   * @param t - the exit tile
   */
  private void setExit(Tile t) throws MultipleExitException{
    Boolean inTiles = false;
    for(int i = 0; i < this.dimensions[1]; i++){
      for(int j = 0; j < this.dimensions[0]; j++){
          if(this.getTileAtLocation(new Maze.Coordinate(j,0)) == t){
            inTiles = true;
          }
      }
    }
    if(inTiles){
      if(this.exit == null){
        this.exit = t;
      } else {
        throw new MultipleExitException("Multiple exits found");
      }
    }

  }
  /**
   * @return the enterance attribute Tile
   */
  public Tile getEntrance(){
    return this.entrance;
  }
  /**
   * @return the exit atrribute Tile
   */
  public Tile getExit(){
    return this.exit;
  }
  /**
   * @return a 2d list containing the tiles representing the maze
   */
  public List<List<Tile>> getTiles(){
    return this.tiles;
  }
  /**
   * @param newTiles - a new 2d list of Tiles
   */
  private void setTiles(List<List<Tile>> newTiles){
    this.tiles = newTiles;
  }
  /**
   * @param target - a coordinate object with coordinates of the tile you want
   * @return the tile at the coordinates of target
   */
  public Tile getTileAtLocation(Coordinate target){
    return this.getTiles().get(target.getY()).get(target.getX());
  }
  /**
   * @param len - sets the x dimension of the 2d list
   * @param height - sets the y dimension of the 2d list
   */
  private void setDimensions(int len, int height){
    this.dimensions = new int[]{len, height};
  }
  /**
   * @return returns an array containing the dimensions of tiles
   */
  public int[] getDimensions(){
    return this.dimensions;
  }
  /**
   * Directions that can be used
   */
  public enum Direction{
    /**
     * North direction
     */
    NORTH,
    /**
     * South direction
     */
    SOUTH,
    /**
     * East direction
     */
    EAST,
    /**
     * West direction
     */
    WEST;
  }
  /**
   * Static class coordinate, used to hold x,y of a tile
   */
  public static class Coordinate implements Serializable{
    /**
     * Stores the x coordinate
     */
    private int x;
    /**
     * Stores the y coordinate
     */
    private int y;
    /**
     * Constructer for a coordinate
     */
    public Coordinate(int i, int j){
      this.x = i;
      this.y = j;
    }
    /**
     * returns x coord
     */
    public int getX(){
      return this.x;
    }
    /**
     * returns y coord
     */
    public int getY(){
      return this.y;
    }
    /**
     * returns a string interpretation in the form (x, y)
     */
    public String toString(){
      return "(" + this.x + ", " + this.y + ")";
    }
  }
}
