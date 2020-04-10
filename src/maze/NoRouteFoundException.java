package maze;

public class NoRouteFoundException extends RuntimeException{
  public NoRouteFoundException(){
    super("No route found through maze");
  }
}
