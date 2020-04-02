package maze;
import java.io.*;
public class Coordinate implements Serializable{
  private int x;
  private int y;
  public Coordinate(int i, int j){
    this.x = i;
    this.y = j;
  }
  public int getX(){
    return this.x;
  }
  public int getY(){
    return this.y;
  }
  public String toString(){
    return "(" + this.x + "," + this.y + ")";
  }
}
