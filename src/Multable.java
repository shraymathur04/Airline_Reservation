import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;

public class Multable extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Multiplication Table");
        FlowPane fp = new FlowPane();
        fp.setAlignment(Pos.CENTER);
        TextField tf = new TextField();
        tf.setPromptText("Enter a number");
        Label lb = new Label("Table =");
        Button genB = new Button("Generate Table");
    
        fp.setVgap(10);
        fp.setHgap(10);
        genB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                try{
                    int num = Integer.parseInt(tf.getText());
                    String table = " "; //"The Multiplication table of "+num+" is \n";
                    for (int i = 0; i <= 10; i++) {
                        table=table +  num + "x" + i + "=" + num * i + "\n";
                 }
                    lb.setText(table);
            }
            catch(NumberFormatException e){
                lb.setText("please enter a valid number");
            }

            }
        });
        fp.getChildren().addAll(lb,tf,genB);

        Scene scene = new Scene(fp, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import javafx.scene.layout.FlowPane;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.geometry.Pos;

// public class Multable extends Application {
//     public static void main(String[] args) {
//         launch(args);
//     }

//     @Override
//     public void start(Stage primaryStage) {
//         primaryStage.setTitle("Multiplication Table");
        
//         FlowPane fp = new FlowPane();
//         fp.setAlignment(Pos.CENTER);
//         fp.setVgap(10);
//         fp.setHgap(10);
        
//         TextField tf = new TextField();
//         tf.setPromptText("Enter a number");
        
//         Button genB = new Button("Generate Table");
        
//         Label lb = new Label("Table =");
        
//         // Set up the button action
//         genB.setOnAction(new EventHandler<ActionEvent>() {
//             @Override
//             public void handle(ActionEvent ae) {
//                 try {
//                     int num = Integer.parseInt(tf.getText());
//                     String table = "Table =\n";
                    
//                     // Generate multiplication table
//                     for (int i = 1; i <= 10; i++) {
//                         table += num + " x " + i + " = " + (num * i) + "\n";
//                     }
//                     lb.setText(table);
//                 } catch (NumberFormatException e) {
//                     lb.setText("Please enter a valid integer.");
//                 }
//             }
//         });
        
//         fp.getChildren().addAll(tf, genB, lb);
        
//         Scene scene = new Scene(fp, 500, 250);
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }
// }