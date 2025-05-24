package hust.soict.hedspi.swing;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PainterController implements Initializable {

    @FXML
    private Pane drawingAreaPane;

    @FXML
    private RadioButton penRadio;

    @FXML
    private RadioButton eraserRadio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    void clearButtonPressed() {
        drawingAreaPane.getChildren().clear();
    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {
        Color brushColor;
        if (penRadio.isSelected()) {
            brushColor = Color.BLACK;
        } else {
            brushColor = Color.WHITE;  // Eraser uses white color (same as canvas)
        }
        
        Circle newCircle = new Circle(event.getX(), event.getY(), 4, brushColor);
        drawingAreaPane.getChildren().add(newCircle);
    }

} 