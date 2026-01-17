import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;

public class credentials extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("JavaFx welcome");
        GridPane gp= new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);
        
        Label wel= new Label("Welcome");
        Label usr_nm= new Label("User Name:");
        TextField tf=new TextField();
        tf.setPromptText("UserName");
        Label pass= new Label("Password:");
        TextField tg= new TextField();
        tg.setPromptText("Password");
        Label ls=new Label();

        Button login=new Button("Sign-in");

        login.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent ae){
                ls.setText("Welcome " + tf.getText());
            }
        });

        gp.add(wel,0,0);gp.add(usr_nm,0,1);gp.add(tf,1,1);
        gp.add(pass,0,2);gp.add(tg,1,2);
        gp.add(login,1,3);
        gp.add(ls,0,4);
        Scene scene= new Scene(gp,600,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
