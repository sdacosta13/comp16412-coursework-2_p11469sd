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
  public final int cellSize = 50;
  public Stage window;
  private Maze mazeToSolve;
  private RouteFinder mazeSolver;
  private Boolean mazeSolved = false;
  @Override
  public void start(Stage primaryStage){
    this.window = primaryStage;
    this.window.setTitle("Maze Solver");
    Button[] buts = this.getButtons();


    Group buttons = new Group();
    for(Button but : buts){
      buttons.getChildren().add(but);
    }
    Scene newScene = new Scene(buttons, 800,600, Color.GREY);
    this.window.setScene(newScene);
    this.window.show();
  }
  public Scene getLoadMazeSolver(){
    return null;
  }
  public Button[] getButtons(){
    Button load = new Button("Load Maze");
    Button loadR = new Button("Load Route");
    Button saveR = new Button("Save Route");
    load.setPrefSize(100,30);
    load.setLayoutX(0);
    load.setLayoutY(0);
    load.setOnAction(this::loadMazeHandler);

    loadR.setPrefSize(100,30);
    loadR.setLayoutX(0);
    loadR.setLayoutY(30);
    loadR.setOnAction(this::loadRoute);
    saveR.setPrefSize(100,30);
    saveR.setLayoutX(0);
    saveR.setLayoutY(60);
    saveR.setOnAction(this::saveRoute);
    Button[] buts = new Button[3];
    buts[0] = load;
    buts[1] = loadR;
    buts[2] = saveR;
    return buts;
  }
  private void doStep(ActionEvent evt){
    if(!this.mazeSolved){
      try{
        this.mazeSolved = this.mazeSolver.step();
      } catch (NoRouteFoundException e){
        System.out.println("No route found");
      }
      Button[] buts = this.getButtons();
      Group objs = new Group();
      for(Button but : buts){
        objs.getChildren().add(but);
      }

      ArrayList<Rectangle> rects = this.generateMazeTiles();
      objs.getChildren().addAll(rects);
      Button step = new Button("Step");
      step.setPrefSize(100,30);
      step.setLayoutX(0);
      step.setLayoutY(90);
      step.setOnAction(this::doStep);
      objs.getChildren().add(step);
      Scene newScene = new Scene(objs, 800,600, Color.GREY);
      this.window.setScene(newScene);

    }


  }
  private void loadRoute(ActionEvent evt){
    FileChooser fl = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("OBJECT files (*.obj)","*.obj");
    fl.getExtensionFilters().add(filter);
    File selected = null;
    selected = fl.showOpenDialog(this.window);
    if(selected != null){
      String path = selected.getAbsolutePath();
      this.mazeSolver = RouteFinder.load(path);
      this.mazeSolved = this.mazeSolver.isFinished();
      this.mazeToSolve = this.mazeSolver.getMaze();
      Button[] buts = this.getButtons();
      Group objs = new Group();
      for(Button but : buts){
        objs.getChildren().add(but);
      }

      ArrayList<Rectangle> rects = this.generateMazeTiles();
      objs.getChildren().addAll(rects);
      Button step = new Button("Step");
      step.setPrefSize(100,30);
      step.setLayoutX(0);
      step.setLayoutY(90);
      step.setOnAction(this::doStep);
      objs.getChildren().add(step);
      Scene newScene = new Scene(objs, 800,600, Color.GREY);
      this.window.setScene(newScene);
    }
  }

  private void saveRoute(ActionEvent evt){
    FileChooser fl = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("OBJECT files (*.obj)","*.obj");
    fl.getExtensionFilters().add(filter);
    File selected = null;
    selected = fl.showSaveDialog(this.window);
    if(selected != null){
      String path = selected.getAbsolutePath();
      this.mazeSolver.save(path);
    }
  }

  private void loadMazeHandler(ActionEvent evt){
    this.mazeSolved = false;
    FileChooser fl = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");
    fl.getExtensionFilters().add(filter);
    File selected = null;
    selected = fl.showOpenDialog(this.window);
    if(selected != null){
      String path = selected.getAbsolutePath();
      try{
        Maze m = Maze.fromTxt(path);
        RouteFinder newRouteFinder = new RouteFinder(m);
        this.mazeToSolve = m;
        this.mazeSolver = newRouteFinder;

        Button[] buts = this.getButtons();
        Group objs = new Group();
        for(Button but : buts){
          objs.getChildren().add(but);
        }

        ArrayList<Rectangle> rects = this.generateMazeTiles();
        objs.getChildren().addAll(rects);
        Button step = new Button("Step");
        step.setPrefSize(100,30);
        step.setLayoutX(0);
        step.setLayoutY(90);
        step.setOnAction(this::doStep);
        objs.getChildren().add(step);
        Scene newScene = new Scene(objs, 800,600, Color.GREY);
        this.window.setScene(newScene);


      } catch (InvalidMazeException e){
        System.out.println("Something went wrong loading " + path);
      }
    }
  }
  private ArrayList<Rectangle> generateMazeTiles(){
    String stringMaze = this.mazeSolver.toString();
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
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException{
    launch(args);
  }

}
