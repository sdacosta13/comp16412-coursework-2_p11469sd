package maze.visualisation;
import javafx.scene.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.event.*;
import maze.*;
import maze.routing.RouteFinder;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;



public class LoadMazeHandle implements EventHandler<MouseEvent>{
  public Stage stage;
  public Group buttons;
  public MazeApplication mApp;
  public LoadMazeHandle(Stage stage, Group buttons, MazeApplication mApp){
    this.stage = stage;
    this.buttons = buttons;
    this.mApp = mApp;
  }
  @Override
  public void handle(MouseEvent event){
    this.buttons = this.mApp.setupButtons();
    FileChooser fl = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");
    fl.getExtensionFilters().add(filter);
    File selected = null;
    selected = fl.showOpenDialog(this.stage);
    if(selected != null){
      String path = selected.getAbsolutePath();
      try{
        Maze m = Maze.fromTxt(path);
        RouteFinder newRouteFinder = new RouteFinder(m);
        this.mApp.setRouteFinder(newRouteFinder);
        ArrayList<Rectangle> rects = this.mApp.printRouteFinder(newRouteFinder.toString());
        this.buttons.getChildren().addAll(rects);
        this.buttons.getChildren().get(0).setVisible(true);
        this.mApp.changeScene(this.buttons);
      } catch (InvalidMazeException e){
        System.out.println("Something went wrong loading " + path);
      }
    }
  }
}
