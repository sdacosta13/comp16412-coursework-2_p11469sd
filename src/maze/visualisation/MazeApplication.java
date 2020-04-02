package maze.visualisation;
import javafx.scene.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import maze.*;
import maze.routing.RouteFinder;

public class MazeApplication extends Application{
  public Group objs;
  public Stage stage;
  public final int cellSize = 50;
  private RouteFinder route;
  public void start(Stage stage) throws InvalidMazeException{
    this.stage = stage;
    Group newButtons = this.setupButtons();
    Scene newScene = new Scene(newButtons, 800,600);
    this.stage.setScene(newScene);
    this.stage.show();
  }
  public void changeScene(Group newObjs){
    Group tempGroup = new Group();
    tempGroup.getChildren().addAll(newObjs.getChildren());
    this.stage.hide();
    Scene newScene = new Scene(tempGroup);
    this.stage.setScene(newScene);
    this.stage.show();
  }
  public Group getBaseGroup(){
    return null;
  }

  public Group setupButtons(){
    Button load = new Button("Load Maze");
    Button loadR = new Button("Load Route");
    Button saveR = new Button("Save Route");
    load.setPrefSize(100,30);
    load.setLayoutX(0);
    load.setLayoutY(0);

    loadR.setPrefSize(100,30);
    loadR.setLayoutX(0);
    loadR.setLayoutY(30);
    saveR.setPrefSize(100,30);
    saveR.setLayoutX(0);
    saveR.setLayoutY(60);

    Button step = new Button("Step");
    step.setPrefSize(100,30);
    step.setLayoutX(0);
    step.setLayoutY(90);
    step.setVisible(false);

    Group buttons = new Group(step, load, loadR, saveR);
    load.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadMazeHandle(this.stage, buttons, this));
    return buttons;
  }
  public ArrayList<Rectangle> printRouteFinder(String stringMaze){
    int yOffset = 120;
    ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    int x = 0;
    int y = 0;
    Rectangle newRect;
    for(int i=0; i < stringMaze.length(); i++){
      if (stringMaze.charAt(i) != '\n'){
        newRect = new Rectangle(x*this.cellSize, (y*this.cellSize) + yOffset, cellSize,cellSize);
        if(stringMaze.charAt(i) == '-'){
          newRect.setFill(Color.YELLOW);
        } else if (stringMaze.charAt(i) == '#'){
          newRect.setFill(Color.BLACK);
        } else if (stringMaze.charAt(i) == '*'){
          newRect.setFill(Color.GREEN);
        } else if (stringMaze.charAt(i) == '.'){
          newRect.setFill(Color.WHITE);
        }
        x+= 1;
        rects.add(newRect);
      } else {
        // in event of \n
        x = 0;
        y += 1;
      }
    }
    return rects;
  }

  public RouteFinder getRouteFinder(){
    return this.route;
  }
  public void setRouteFinder(RouteFinder r){
    this.route = r;
  }
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException{
    launch(args);
  }
}
