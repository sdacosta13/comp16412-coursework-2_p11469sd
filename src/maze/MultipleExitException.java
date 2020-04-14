package maze;
/**
 * Basic class to throw a MultipleExitException
 */
public class MultipleExitException extends InvalidMazeException{
  /**
   * @param errorMessage - message to be thown
   */
  public MultipleExitException(String errorMessage){
    super(errorMessage);
  }
}
