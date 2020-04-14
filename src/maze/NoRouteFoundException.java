package maze;
/**
 * Basic class to throw a NoRouteFoundException
 */
public class NoRouteFoundException extends RuntimeException{
  public NoRouteFoundException(){
    super("No route found through maze");
  }
}
