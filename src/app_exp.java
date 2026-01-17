import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;


public class app_exp extends Application {//import application class
    public void start(Stage primaryStage){
        primaryStage.setTitle("This is the first javafx application");
        Label lb=new Label("Welcome to JavaFx Programming");
        lb.setTextFill(Color.MAGENTA);
        FlowPane fp= new FlowPane(10,10);
        fp.getChildren().add(lb);// adds label to scene
        fp.setAlignment(Pos.CENTER);
        fp.setVgap(10);
        fp.setHgap(0);
        
        Scene scene= new Scene(fp,500,200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
