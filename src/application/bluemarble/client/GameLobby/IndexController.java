package application.bluemarble.client.GameLobby;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController {


    @FXML private Label lbMessage;


    @FXML
    void onClickCancel(ActionEvent e) throws IOException {
        Node node = (Node)(e.getSource());
        Stage stage = (Stage) (node.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("../MainUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("미니게임");
        stage.setScene(scene);
        stage.show();
    }


}
