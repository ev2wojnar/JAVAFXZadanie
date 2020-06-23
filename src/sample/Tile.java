package sample;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Tile extends StackPane {
    private Text text = new Text();

    public Tile(String value){
        Rectangle border = new Rectangle(50,50);
        border.setFill(null);
        border.setStroke(Color.STEELBLUE);

        text.setText(value);
        text.setFont(Font.font(30));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
        //open();
//        setOnMouseClicked();
//        close();
    }
    public void open(){

        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
        ft.setToValue(1);
        ft.play();
    }
    public void close(){
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
        ft.setToValue(0);
        ft.play();
    }
}
