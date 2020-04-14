package maze.routing;
import java.util.Stack;
import maze.*;
import java.io.*;
import java.util.List;
/**
 * Class to appy the route finding algorithm
 */
public class RouteFinder implements Serializable{

  private Maze maze;
  private Stack<Tile> route;
  private boolean finished = false;
  private BoolArray bMap;
  public final Maze.Direction[] dirs = {Maze.Direction.WEST, Maze.Direction.NORTH, Maze.Direction.EAST, Maze.Direction.SOUTH};//Priorities search from left or right of this list
  /**
   * Constructor to setup new unsolved mazeSolved
   * @param maze - maze to be solved
   */
  public RouteFinder(Maze maze){
    this.route = new Stack<Tile>();
    this.maze = maze;
    this.bMap = new BoolArray(this.maze.getDimensions());
    route.push(this.maze.getEntrance());

  }
  /**
   * A call to this function will take a step through the maze
   * @return a bool representing if the maze is finished or not
   */
  public boolean step() throws NoRouteFoundException{
    if (!this.isFinished()){
      Tile initial = route.pop();
      route.push(initial);
      boolean dead = true;
      for (int i = 0; i < 4; i++){
        Tile next = this.maze.getAdjacentTile(initial, dirs[i]);
        if(next.getType() != Tile.Type.WALL && !this.route.contains(next) && !this.bMap.isVisited(next.getCoords())){
          route.push(next);
          if(next.getType() == Tile.Type.EXIT){
            this.finished = true;
          }
          this.bMap.visit(next.getCoords());
          dead = false;
          break;
        }
      }
      if(dead){
        Tile toDie;
        toDie = route.pop();
      }
    }
    if(this.route.isEmpty()){
      throw new NoRouteFoundException();
    }
    return this.isFinished();
  }
  /**
   * @return the Maze being solved
   */
  public Maze getMaze(){
    return this.maze;
  }
  /**
   * @return the current route through the maze, not neccesarily fully solved
   */
  public List<Tile> getRoute(){
    return this.route;
  }
  /**
   * @return the state the routefinding algorithm is in
   */
  public boolean isFinished(){
    return this.finished;
  }
  /**
   * Constructor for RouteFinder, used to read in .route objects
   * @param path - the path to the .route object
   */
  public static RouteFinder load(String path){
    RouteFinder r = null;
    try{
      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Object obj = ois.readObject();
      ois.close();
      r = (RouteFinder) obj;

    } catch (IOException e){
      System.out.println("IOException occured");

    } catch (ClassNotFoundException e){
      System.out.println("Class: %s not found".format(path));

    }
    return r;
  }
  /**
   * Method to save the object to a .route object
   * @param path - the path denoting where the .route object should be stored
   */
  public void save(String path) throws IOException{
    try{
      FileOutputStream fos = new FileOutputStream(path);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found exception occured");


    }
  }
  /**
   * Overlays the bMap.toString() method, maze.toString() method, and the path
   * <p> Generates a printable string with the following tiles denoted differently
   * <p><ol>
   * <li> unvisted path tiles
   * <li> visited path tiles
   * <li> route tiles
   * <li> wall tiles
   * <\ol>
   * @return a printable string represntation of the maze
   */
  public String toString(){
    //Draw bMap first
    //Overwrite with Maze map
    //Overwrite with path
    char[] returnString = bMap.toString().toCharArray();
    for(int y = 0; y < this.maze.getDimensions()[1]; y++){
      for(int x = 0; x < this.maze.getDimensions()[0]; x++){
        String s = this.maze.getTileAtLocation(new Maze.Coordinate(x,y)).toString();
        //account for \n
        if(s == "#"){
          int pos = y*(this.maze.getDimensions()[0]+1)+x;
          returnString[pos] = s.charAt(0);
        }
      }
    }

    for(int i = 0; i < route.size(); i++){
      if(route.get(i) != null){
        Maze.Coordinate c = route.get(i).getCoords();
        int pos = c.getY()*(this.maze.getDimensions()[0]+1)+c.getX();
        returnString[pos] = '*';
      }
    }

    for(int y = 0; y < this.maze.getDimensions()[1]; y++){
      for(int x = 0; x < this.maze.getDimensions()[0]; x++){
        int pos = y*(this.maze.getDimensions()[0]+1)+x;
        char s = returnString[pos];
        if(s == '0'){
          returnString[pos] = '.';
        } else if (s == '1'){
          returnString[pos] = '-';
        }
      }
    }
    String newString = "";
    for(int i = 0; i < returnString.length; i++){
      newString += returnString[i];
    }
    return newString;
  }
}
