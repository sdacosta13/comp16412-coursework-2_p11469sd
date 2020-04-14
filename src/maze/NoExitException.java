package maze;
/**
 * Basic class to throw a NoExitException
 */
public class NoExitException extends InvalidMazeException{
  /**
   * @param errorMessage - message to be thown
   */
  public NoExitException(String errorMessage){
    super(errorMessage);
  }
}
