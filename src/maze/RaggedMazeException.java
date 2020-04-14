package maze;
/**
 * Basic class to throw a MultipleEntranceException
 * should be thrown when a maze being read has different length on each line
 */
public class RaggedMazeException extends InvalidMazeException{
  /**
   * @param errorMessage - message to be thown
   */
  public RaggedMazeException(String errorMessage){
    super(errorMessage);
  }
}
