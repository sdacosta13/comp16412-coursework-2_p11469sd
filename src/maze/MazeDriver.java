package maze;
import java.io.*;
import java.util.List;
import javafx.scene.Scene;
public class MazeDriver{
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException{
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
    Maze newMaze = Maze.fromTxt("/home/sam/GitRepos/comp16412-coursework-2_p11469sd/mazes/valid/maze1.txt");
    System.out.print(newMaze.toString());
    System.out.println(newMaze.getDimensions()[0]+ "," + newMaze.getDimensions()[1]);

  }
}
