import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1024,768);
        stage.setTitle("Tournament Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
