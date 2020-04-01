package maze;

public class NoRouteFoundException extends Exception{
  public NoRouteFoundException(){
    super("No route found through maze");
  }
}
