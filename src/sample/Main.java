package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Main extends Application {

//    public static final int NUM_OF_PAIRS = 50;
    public static final int NUM_PER_ROW = 10;
    public static final int PLANCK_TIME = 50;


    private Parent update(){
        Pane root = new Pane();
        root.setPrefSize(600, 600);
        for(int i = 0; i < NUM_PER_ROW*NUM_PER_ROW; i++){
            int column = (i % NUM_PER_ROW);
            int row = i / NUM_PER_ROW;
            Number occupant = NumbersMovementsService.getOccupant(column+1, row+1);

            Tile tile;
            if (occupant == null){
                tile = new Tile(" ");
            } else {
                tile = new Tile(occupant.toString());
            }
            tile.setTranslateX(50 * column);
            tile.setTranslateY(50 * row);
            root.getChildren().add(tile);
        }
        return root;
    }

    @Override
    public void start(final Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        NumbersMovementsService.initialize();
        primaryStage.setTitle("Hello from JavaFX");
        //primaryStage.setScene(new Scene(createContent()));
        //primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setScene(new Scene(update()));
        primaryStage.show();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(PLANCK_TIME), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        primaryStage.setScene(new Scene(update()));
                    }
        }));
        timeline.playFromStart();
    }




    public static void main(String[] args) {

        launch(args);
    }


}
