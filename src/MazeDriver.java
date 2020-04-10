import maze.*;
import java.io.*;
import java.util.List;
import maze.routing.RouteFinder;
import javafx.scene.Scene;
public class MazeDriver{
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException{
    Maze rtn = null;
    try {
        rtn = Maze.fromTxt("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/noEntrance.txt");
        System.out.println(rtn.toString());
    } catch (Exception e) {
      System.out.println("Fail");
      e.printStackTrace();
    }

  }
}
