package maze;
/**
 * Basic class to throw a MultipleEntranceException
 */
public class MultipleEntranceException extends InvalidMazeException{
  /**
   * @param errorMessage - message to be thown
   */
  public MultipleEntranceException(String errorMessage){
    super(errorMessage);
  }
}
