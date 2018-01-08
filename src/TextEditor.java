
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 This is the main application class for the Text Editor JavaFX application
 @author Peer-Anders
 */
public class TextEditor extends Application
{

    public void start(Stage stage) throws Exception
    {
        //load fxml file
        Parent parent = FXMLLoader.load(
                getClass().getResource("TextEditor.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        // Launch the application.
        launch(args);
    }
}
