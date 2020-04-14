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
import java.io.*;
import java.util.ArrayList;
import maze.*;
import maze.routing.RouteFinder;

/**
 * main application class to be run
 */
public class MazeApplication extends Application{
  public final int cellSize = 50;
  public Stage window;
  private Maze mazeToSolve;
  private RouteFinder mazeSolver;
  private Boolean mazeSolved = false;
  public int targetX = 800;
  public int targetY = 600;
  /**
   * called at the start of the program
   */
  @Override
  public void start(Stage primaryStage){
    this.window = primaryStage;
    this.window.setTitle("Maze Solver");
    Button[] buts = this.getButtons();


    Group buttons = new Group();
    for(Button but : buts){
      buttons.getChildren().add(but);
    }
    Scene newScene = new Scene(buttons, targetX, targetY, Color.GREY);
    this.window.setScene(newScene);
    this.window.show();
  }
  /**
   * @return a list of the main 3 buttons
   */
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
  /**
   * Called when the step button is clicked.
   * this will call step() once and change the state of the maze on screen
   */
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
      Scene newScene = new Scene(objs, targetX, targetY, Color.GREY);
      this.window.setScene(newScene);

    }


  }
  /**
   * Called when the load route button is clicked
   * This will load the maze and route finder objects and their states
   * this will also display the maze and step button
   */
  private void loadRoute(ActionEvent evt){
    FileChooser fl = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("ROUTE files (*.route)","*.route");
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
      Scene newScene = new Scene(objs, targetX, targetY, Color.GREY);
      this.window.setScene(newScene);
    }
  }
  /**
   * Called when the save button is clicked
   * This will open a FileChooser widget to select a path
   * this will then save the file
   */
  private void saveRoute(ActionEvent evt){
    FileChooser fl = new FileChooser();
    File selected = null;
    selected = fl.showSaveDialog(this.window);
    if(selected != null){
      String path = selected.getAbsolutePath();
      try{
        this.mazeSolver.save(path+".route");
      } catch (IOException e){
        System.out.println("IO Error occured");
      }
    }
  }
  /**
   * Called when the load maze button is clicked.
   * this will load an display a maze to the screen. It will get the path via FileChooser
   */
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
  /**
   * Used to convert the RouteFinder.toString() method to Rectangle's in the correct position
   * @return A list of rectangles configured to display a maze
   */
  private ArrayList<Rectangle> generateMazeTiles(){
    String stringMaze = this.mazeSolver.toString();
    int yOffset = 120;
    ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    int x = 0;
    int y = this.mazeSolver.getMaze().getDimensions()[1]-1;
    targetX = this.mazeSolver.getMaze().getDimensions()[0]*cellSize + cellSize;
    targetY = this.mazeSolver.getMaze().getDimensions()[1]*cellSize + yOffset + cellSize;
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
        y -= 1;
      }
    }
    return rects;
  }
  public static void main (String args[]) throws InvalidMazeException, OutOfMazeException, NoRouteFoundException{
    launch(args);
  }

}
