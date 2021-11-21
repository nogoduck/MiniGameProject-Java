package application.Bluemarble.GameRoom;

import application.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameRoomController implements Initializable {











    @FXML void onClickRunDice(ActionEvent e) {

    }


    @FXML
    void onHoverEnter(MouseEvent e) {
        Node source = (Node)e.getSource();
        source.setStyle("-fx-cursor:hand;");
    }
    @FXML
    void onHoverExit(MouseEvent e) {
        Node source = (Node)e.getSource();
        source.setStyle("-fx-cursor:default;");
    }

//    ==================================================
//                 Start Bluemarble Modal
//    ==================================================
    @FXML private AnchorPane apStartBluemarbleModal;
    @FXML private ToggleGroup PlayerCntGroup;
    @FXML private ToggleGroup distMoneyGroup;
    @FXML private RadioButton rb2Player;
    @FXML private RadioButton rb3Player;
    @FXML private RadioButton rb4Player;
    @FXML private RadioButton rbDefaultDistMoney;
    @FXML private RadioButton rbCustomDistMoney;
    @FXML void onCloseCreateRoomModal(MouseEvent e) throws IOException {
        Node node = (Node)(e.getSource());
        Stage stage = (Stage)(node.getScene().getWindow());
        Parent root = FXMLLoader.load(MainController.class.getResource("MainUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("부루마블");
        stage.setScene(scene);
        stage.show();
    }
    @FXML void onSubmitCreateRoomModal(MouseEvent e) {
        System.out.println("확인");
    }
    void initStartBluemarbleModal(){
        rbDefaultDistMoney.setSelected(true);
        rb2Player.setSelected(true);
        apStartBluemarbleModal.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStartBluemarbleModal();
    }
}
