package maze;

public class Tile{
  private Type type;
  private boolean navigable;
  private Tile(Type type){
    this.type = type;
    if(this.type == Type.WALL){
      this.navigable = false;
    } else {
      this.navigable = true;
    }
  }
  protected static Tile fromChar(char Char){
    Type newType = Type.WALL;
    switch (Char){
      case '.':
        newType = Type.CORRIDOR;
        break;
      case '#':
        newType = Type.WALL;
        break;
      case 'e':
        newType = Type.ENTRANCE;
        break;
      case 'x':
        newType = Type.EXIT;
        break;
    }
    return new Tile(newType);
  }
  public Type getType(){
    return this.type;
  }
  public boolean isNavigable(){
    return navigable;
  }
  public String toString(){
    switch (this.type){
      case CORRIDOR:
        return ".";
      case WALL:
        return "#";
      case ENTRANCE:
        return "e";
      case EXIT:
        return "x";
    }
    return null;
  }
}
