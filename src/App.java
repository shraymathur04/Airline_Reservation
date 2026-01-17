import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label with text "Hello, World!"
        Label helloLabel = new Label("Hello, World!");

        // Create a layout pane and add the label to it
        StackPane root = new StackPane();
        root.getChildren().add(helloLabel);

        // Create a scene with the layout pane and set the dimensions
        Scene scene = new Scene(root, 300, 200);

        // Set the title of the window
        primaryStage.setTitle("Hello World JavaFX");
        
        // Set the scene to the stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}