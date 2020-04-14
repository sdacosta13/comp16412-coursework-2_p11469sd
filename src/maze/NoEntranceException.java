package maze;
/**
 * Basic class to throw a NoEntranceException
 */
public class NoEntranceException extends InvalidMazeException{
  /**
   * @param errorMessage - message to be thown
   */
  public NoEntranceException(String errorMessage){
    super(errorMessage);
  }
}
