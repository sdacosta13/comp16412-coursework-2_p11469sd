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
import maze.*;

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
          this.setRouteFinder(RouteFinder(m));
          int yOffset = 90;

          this.stage.hide();
          newGroup.getChildren().addAll(objs.getChildren());

          for(int y = 0; y < m.getDimensions()[1]; y++){
            for(int x = 0; x < m.getDimensions()[0]; x++){
              Tile t = m.getTileAtLocation(new Coordinate(x,y));
              Rectangle newRect = new Rectangle(x*this.cellSize,(y*this.cellSize)+yOffset,this.cellSize,this.cellSize);

              if(t.getType() == Type.CORRIDOR){
                newRect.setFill(Color.GREY);
              } else if (t.getType() == Type.WALL){
                newRect.setFill(Color.BLACK);
              } else if (t.getType() == Type.ENTRANCE){
                newRect.setFill(Color.YELLOW);
              } else {
                newRect.setFill(Color.RED);
              }
              newGroup.getChildren().add(newRect);
            }
          }
          Button step = new Button("Step");
          step.setPrefSize(90,30);
          step.setLayoutX(0);
          step.setLayoutY(yOffset + (m.getDimensions()[1]*this.cellSize));

          step.setOnAction((f) -> {
            this.getRouteFinder().step();
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
    //r.setFill(Color.WHITE);
    Group newGroup = new Group(load, loadR, saveR);
    return newGroup;
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
  public void handleLoad(ActionEvent e){

  }
  public void parseMaze(Maze m){
    ;
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
