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
import java.io.File;
import javafx.scene.input.MouseEvent;

public class LoadMazeHandle implements EventHandler<ActionEvent>{
  public Stage stage;
  public String path;
  public LoadMazeHandle(Stage s){
    this.stage = s;
  }
  @Override
  public void handle(ActionEvent event){
    FileChooser fl = new FileChooser();
    File selected = fl.showOpenDialog(this.stage);
    if(selected != null){
      this.path = selected.getAbsolutePath();
    }
  }

}
