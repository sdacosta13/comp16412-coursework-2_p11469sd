package maze;
/**
 * Basic class to throw a OutOfMazeException
 */
public class OutOfMazeException extends Exception{
  /**
   * @param x the x coordinate of the tile not in the maze
   * @param y the y coordinate of the tile not in the maze
   */
  public OutOfMazeException(int x, int y){
    super(String.format("Adjacent Tile x = %d, y = %d does not exist",x,y));
  }
}
