package maze;
import java.io.*;
import java.util.ArrayList;
public class Maze implements Serializable{
  private Tile entrance;
  private Tile exit;
  private ArrayList<ArrayList<Tile>> tiles;
  private int[] dimensions;
  private Maze(){
    ;
  }
  public String toString(){
    String returnString = "";
    for(int y = 0; y < this.getDimensions()[1]; y++){
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
        y -= 1;
        break;
      case SOUTH:
        y += 1;
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
    Maze newMaze = new Maze();
    ArrayList<ArrayList<Tile>> newTiles = new ArrayList<ArrayList<Tile>>();
    newTiles.add(new ArrayList<Tile>());
    try{
      FileReader newFile = new FileReader(path);
      int i;
      int dimX = 0;
      int x = 0;
      int y = 0;
      int entriesSeen = 0;
      int exitsSeen = 0;
      Coordinate exitLoc = new Coordinate(0,0);
      Coordinate entryLoc = new Coordinate(0,0);
      boolean setDim = true;
      char curChar = ' ';
      while ((i=newFile.read()) != -1){
        curChar = (char) i;
        if(curChar == 'e'){
          entriesSeen += 1;
          entryLoc = new Coordinate(x,y);
        } else if (curChar == 'x'){
          exitsSeen += 1;
          exitLoc = new Coordinate(x,y);
        } else if (curChar != '#' && curChar != '.' && curChar != '\n'){
          throw new RaggedMazeException(String.format("Char: %c found in maze",curChar));
        }
        if(curChar != '\n'){
          Tile newTile = Tile.fromChar(curChar);
          newTile.setCoords(new Coordinate(x,y));
          newTiles.get(y).add(newTile);
          x+= 1;
          if(setDim){
            //this method is error prone to different line lengths
            dimX += 1;
          }
        } else {
          x = 0;
          setDim = false;
          y += 1;
          newTiles.add(new ArrayList<Tile>());
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

      newMaze.setDimensions(dimX,y);
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
  private void setEntrance(Tile t){
    this.entrance = t;
  }
  private void setExit(Tile t){
    this.exit = t;
  }
  public Tile getEntrance(){
    return this.entrance;
  }
  public Tile getExit(){
    return this.exit;
  }
  public ArrayList<ArrayList<Tile>> getTiles(){
    return this.tiles;
  }
  private void setTiles(ArrayList<ArrayList<Tile>> newTiles){
    this.tiles = newTiles;
  }
  public Tile getTileAtLocation(Coordinate target){
    return this.tiles.get(target.getY()).get(target.getX());
  }
  private void setDimensions(int len, int height){
    this.dimensions = new int[]{len, height};
  }
  public int[] getDimensions(){
    return this.dimensions;
  }
}
