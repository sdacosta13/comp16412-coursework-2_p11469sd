package maze.visualisation;
import javafx.scene.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.event.*;
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
    this.setupButtons();
    Scene scene = new Scene(this.objs, 600,800);
    this.stage.setScene(scene);
    this.stage.setTitle("Maze Solver");
    this.stage.show();

  }
  public Group getBaseGroup(){
    Button load = new Button("Load Maze");
    load.setPrefSize(90,30);
    load.setLayoutX(0);
    load.setLayoutY(0);

    load.setOnAction((e) -> {
      FileChooser fl = new FileChooser();
      FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");
      fl.getExtensionFilters().add(filter);
      File selected = null;
      selected = fl.showOpenDialog(this.stage);
      if(selected != null){
        String path = selected.getAbsolutePath();
        Group newGroup = this.getBaseGroup();
        try{
          Maze m = Maze.fromTxt(path);
          this.setRouteFinder(new RouteFinder(m));
          int yOffset = 90;

          this.stage.hide();
          newGroup.getChildren().addAll(objs.getChildren());

          String stringMaze = this.getRouteFinder().toString();
          ArrayList rects = this.printRouteFinder(stringMaze, yOffset);
          for(int i = 0; i < rects.size(); i++){
            Rectangle newRect = (Rectangle) rects.get(i);
            newGroup.getChildren().add(newRect);
          }



          Button step = new Button("Step");
          step.setPrefSize(90,30);
          step.setLayoutX(0);
          step.setLayoutY(yOffset + (m.getDimensions()[1]*this.cellSize));

          step.setOnAction((f) -> {
            try{
              Group newGroup2 = this.getBaseGroup();
              this.stage.hide();
              this.getRouteFinder().step();
              String stringMaze2 = this.getRouteFinder().toString();
              ArrayList<Rectangle> rects2 = this.printRouteFinder(stringMaze, yOffset);
              for(int i = 0; i < rects.size(); i++){
                Rectangle newRect = (Rectangle) rects.get(i);
                newGroup.getChildren().add(newRect);
              }
              Scene scene = new Scene(newGroup);
              this.stage.setScene(scene);
              this.stage.show();


            } catch(NoRouteFoundException noRoute) {
              System.out.println("No Route found");
            }

          });
          newGroup.getChildren().add(step);
          Scene scene = new Scene(newGroup);
          this.stage.setScene(scene);
          this.stage.show();

        } catch (InvalidMazeException newException){
          System.out.println("Error Loading maze");
        }
      }
    });

    Button loadR = new Button("Load Route");
    loadR.setPrefSize(100,30);
    loadR.setLayoutX(0);
    loadR.setLayoutY(30);
    loadR.setOnAction((e)-> {

    });
    Button saveR = new Button("Save Route");
    saveR.setPrefSize(100,30);
    saveR.setLayoutX(0);
    saveR.setLayoutY(60);
    Group newGroup = new Group(load, loadR, saveR);
    return newGroup;
  }
  public ArrayList<Rectangle> printRouteFinder(String stringMaze,int yOffset){
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
  public void setupButtons() throws InvalidMazeException{
    this.objs = getBaseGroup();

  }
  public void loadMaze(String path) throws InvalidMazeException{

    Scene scene = new Scene(this.objs, 600,800);
    this.stage.setScene(scene);
    this.stage.setTitle("Maze Solver");
    this.stage.show();

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
