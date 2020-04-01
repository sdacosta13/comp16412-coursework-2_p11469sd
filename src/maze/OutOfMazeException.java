package maze;

public class OutOfMazeException extends Exception{
  public OutOfMazeException(int x, int y){
    super(String.format("Adjacent Tile x = %d, y = %d does not exist",x,y));
  }
}
