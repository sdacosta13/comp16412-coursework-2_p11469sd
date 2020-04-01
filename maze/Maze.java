package maze;
import java.io.*;
import java.util.ArrayList;
public class Maze{
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
  public static Maze fromTxt(String path){
    Maze newMaze = new Maze();
    ArrayList<ArrayList<Tile>> newTiles = new ArrayList<ArrayList<Tile>>();
    newTiles.add(new ArrayList<Tile>());
    try{
      FileReader newFile = new FileReader(path);
      int i;
      int x = 0;
      int y = 0;
      boolean countX = true;
      char curChar = ' ';
      //newTiles.add(new ArrayList<Tile>());
      while ((i=newFile.read()) != -1){
        curChar = (char) i;
        if(curChar != '\n'){
          if(countX){
            //this method is error prone to different line lengths
            x += 1;
          }

          Tile newTile = Tile.fromChar(curChar);
          newTiles.get(y).add(newTile);
        } else {
          countX = false;
          y += 1;
          newTiles.add(new ArrayList<Tile>());
        }

      }
      newMaze.setDimensions(x,y);
      newMaze.setTiles(newTiles);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e){
      System.out.println("IOException");
    }
    return newMaze;
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
