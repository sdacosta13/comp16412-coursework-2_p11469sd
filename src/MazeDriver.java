import maze.*;
import java.io.*;
import java.util.List;
import maze.routing.RouteFinder;
import javafx.scene.Scene;
public class MazeDriver{
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException, IOException{
    System.out.println(System.getProperty("user.dir"));
    Maze m = Maze.fromTxt("../mazes/maze1.txt");
    RouteFinder r = new RouteFinder(m);
    r.step();
    r.step();
    r.step();
    r.save("./tests/routes/ensureSaveWritesFile.route");
  }
}
