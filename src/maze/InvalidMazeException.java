package maze;
/**
 * Basic class to create an exception for invalid mazes
 */
public class InvalidMazeException extends Exception{
  /**
   * @param errorMessage - the errorMessage to be thrown
   */
  public InvalidMazeException(String errorMessage){
    super(errorMessage);
  }
}
