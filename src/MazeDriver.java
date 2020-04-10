import maze.*;
import java.io.*;
import java.util.List;
import maze.routing.RouteFinder;
import javafx.scene.Scene;
public class MazeDriver{
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException, IOException{
    Maze m = Maze.fromTxt("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/maze3.txt");
    System.out.print(m.toString());
  }
}
