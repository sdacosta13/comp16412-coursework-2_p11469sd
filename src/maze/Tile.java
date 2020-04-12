package maze;
import java.io.*;
/**
 * Tile to represent a single tile in the maze
 */
public class Tile implements Serializable{
  /**
   * stores the type of the tile - one of [ENTERANCE, EXIT, CORRIDOR, WALL]
   */
  private Type type;
  /**
   * Determines whether the pathing will view the tile as navigable
   */
  private boolean navigable;
  /**
   * Stores the coordinates of the tile in the 2d array
   */
  private Maze.Coordinate coords;
  /**
   * Stores wether the tile has been visited by the pathing algorithm
   */
  private Boolean visited;
  /**
   * Constructer for the tile
   * @param type - the type of tile to be made
   */
  private Tile(Type type){
    this.type = type;
    if(this.type == Type.WALL){
      this.navigable = false;
    } else {
      this.navigable = true;
    }
  }
  /**
   * Static method to create a tile from a character
   * @param Char - the text representation of a tile
   * @return new Tile object
   */
  protected static Tile fromChar(char Char){
    Type newType = Type.WALL;
    switch (Char){
      case '.':
        newType = Type.CORRIDOR;
        break;
      case '#':
        newType = Type.WALL;
        break;
      case 'e':
        newType = Type.ENTRANCE;
        break;
      case 'x':
        newType = Type.EXIT;
        break;
    }
    return new Tile(newType);
  }
  /**
   * @return this.type
   */
  public Type getType(){
    return this.type;
  }
  /**
   * @return wether the tile is navigable
   */
  public boolean isNavigable(){
    return navigable;
  }
  /**
   * @return the textual representation of the Tile
   */
  public String toString(){
    switch (this.type){
      case CORRIDOR:
        return ".";
      case WALL:
        return "#";
      case ENTRANCE:
        return "e";
      case EXIT:
        return "x";
    }
    return null;
  }
  /**
   * @param coords - the coordinates to be stored in the coords attribute
   */
  public void setCoords(Maze.Coordinate coords){
    this.coords = coords;
  }
  /**
   * @return the coords of the Tile
   */
  public Maze.Coordinate getCoords(){
    return this.coords;
  }
  /**
   * @return wether the tile has been visited by the algorithm
   */
  public Boolean getVisited(){
    return this.visited;
  }
  /**
   * @param b - sets wether the tile has been visited
   */
  public void setVisited(Boolean b){
    this.visited = b;
  }
  /**
   * enum to store the type of the tile
   */
  public enum Type{
    CORRIDOR, ENTRANCE, EXIT, WALL;
  }

}
