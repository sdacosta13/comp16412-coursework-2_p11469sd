package maze;
import java.io.*;
public class Tile implements Serializable{
  private Type type;
  private boolean navigable;
  private Maze.Coordinate coords;
  private Boolean visited;
  private Tile(Type type){
    this.type = type;
    if(this.type == Type.WALL){
      this.navigable = false;
    } else {
      this.navigable = true;
    }
  }
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
  public Type getType(){
    return this.type;
  }
  public boolean isNavigable(){
    return navigable;
  }
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
  public void setCoords(Maze.Coordinate coords){
    this.coords = coords;
  }
  public Maze.Coordinate getCoords(){
    return this.coords;
  }
  public Boolean getVisited(){
    return this.visited;
  }
  public void setVisited(Boolean b){
    this.visited = b;
  }
  public enum Type{
    CORRIDOR, ENTRANCE, EXIT, WALL;
  }

}
