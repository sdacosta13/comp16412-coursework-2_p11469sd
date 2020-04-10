package maze;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
public class Maze implements Serializable{
  private Tile entrance = null;
  private Tile exit = null;
  private List<List<Tile>> tiles;
  private int[] dimensions;
  private Maze(){
    ;
  }
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
  private void setEntrance(Tile t) throws MultipleEntranceException{
    Boolean inTiles = false;
    for(int i = 0; i < this.dimensions[1]; i++){
      for(int j = 0; j < this.dimensions[1]; j++){
          if(this.getTileAtLocation(new Maze.Coordinate(i,j)) == t){
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
  private void setExit(Tile t) throws MultipleExitException{
    Boolean inTiles = false;
    for(int i = 0; i < this.dimensions[1]; i++){
      for(int j = 0; j < this.dimensions[1]; j++){
          if(this.getTileAtLocation(new Maze.Coordinate(i,j)) == t){
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
  public Tile getEntrance(){
    return this.entrance;
  }
  public Tile getExit(){
    return this.exit;
  }
  public List<List<Tile>> getTiles(){
    return this.tiles;
  }
  private void setTiles(List<List<Tile>> newTiles){
    this.tiles = newTiles;
  }
  public Tile getTileAtLocation(Coordinate target){
    return this.getTiles().get(target.getY()).get(target.getX());
  }
  private void setDimensions(int len, int height){
    this.dimensions = new int[]{len, height};
  }
  public int[] getDimensions(){
    return this.dimensions;
  }
  public enum Direction{
    NORTH, SOUTH, EAST, WEST;
  }
  public static class Coordinate implements Serializable{
    private int x;
    private int y;
    public Coordinate(int i, int j){
      this.x = i;
      this.y = j;
    }
    public int getX(){
      return this.x;
    }
    public int getY(){
      return this.y;
    }
    public String toString(){
      return "(" + this.x + ", " + this.y + ")";
    }
  }
}
