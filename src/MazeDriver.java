import maze.*;
import java.io.*;
import java.util.List;
import maze.routing.RouteFinder;
import javafx.scene.Scene;
public class MazeDriver{
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException{
    //Coordinate newCoord = new Coordinate(1,2);
    //System.out.println(newCoord.toString());
    /*

    System.out.print(newMaze.getDimensions()[0]+", ");
    System.out.print(newMaze.getDimensions()[1]);
    for(int y = 0; y < newMaze.getDimensions()[1]; y++){
      for(int x = 0; x < newMaze.getDimensions()[0]; x++){
        System.out.println(newMaze.getTileAtLocation(new Coordinate(x,y)).toString());
      }
    }*/
    Maze newMaze = Maze.fromTxt("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/valid/maze2.txt");
    //RouteFinder r = new RouteFinder(newMaze);
    Boolean f = false;
    // for(int i = 0; i < 10; i++){
    //   f = r.step();
    //   System.out.print(r.toString());
    //   System.out.println();
    // }
    // r.Save("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/valid/RouteFinderM1.obj");
    RouteFinder r = RouteFinder.load("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/valid/RouteFinderM2.obj");
    // for(int i = 0; i < 20; i++){
    //   r.step();
    // }
    // r.Save("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/valid/RouteFinderM2.obj")
    System.out.print(r.toString());
  }
}
