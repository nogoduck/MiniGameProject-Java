package application.Bird;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class BirdController {

    @FXML
    private Label lbGood;

    @FXML
    void onToggleButton(ActionEvent event) {
        if(lbGood.getText().equals("Good for you")) lbGood.setText("Bad for you");
        else lbGood.setText("Good for you");
    }

}
