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

public class StepHandle implements EventHandler<MouseEvent>{
  public Stage stage;
  public Group buttons;
  public MazeApplication mApp;
  public StepHandle(Stage stage, Group buttons, MazeApplication mApp){
    this.stage = stage;
    this.buttons = buttons;
    this.mApp = mApp;
  }
  @Override
  public void handle(MouseEvent event){
    try{
      this.buttons = this.mApp.setupButtons();
      this.mApp.getRouteFinder().step();
      ArrayList<Rectangle> rects = this.mApp.printRouteFinder(this.mApp.getRouteFinder().toString());
      this.buttons.getChildren().addAll(rects);
      this.buttons.getChildren().get(0).setVisible(true);
      this.mApp.changeScene(this.buttons);
    } catch (NoRouteFoundException e){
        System.out.println("No Route Found");
    }
  }
}
