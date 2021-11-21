package application.Bluemarble;

import application.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class GameRoomController implements Initializable {











    @FXML void onClickRunDice(ActionEvent e) {
    }


//     마우스 호버 액션
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
    DecimalFormat df =  new DecimalFormat("###,###");
    @FXML private AnchorPane apStartBluemarbleModal;
    @FXML private ToggleGroup PlayerCntGroup;
    @FXML private ToggleGroup startDistMoneyGroup;
    @FXML private RadioButton rb2Player;
    @FXML private RadioButton rb3Player;
    @FXML private RadioButton rb4Player;
    @FXML private RadioButton rbDefaultDistMoney;
    @FXML private RadioButton rbCustomDistMoney;
    @FXML private TextField tfStartDiskMoney;
    @FXML void onChangeStartDistMoney(KeyEvent e) {
        //숫자, 지우기 키만 허용 46 - 57
       switch(e.getCode()){
           case BACK_SPACE: break;
           case DELETE: break;
           case DIGIT0: break;
           case DIGIT1: break;
           case DIGIT2: break;
           case DIGIT3: break;
           case DIGIT4: break;
           case DIGIT5: break;
           case DIGIT6: break;
           case DIGIT7: break;
           case DIGIT8: break;
           case DIGIT9: break;
           default:
               String str = tfStartDiskMoney.getText().replace(e.getCode().toString(), "");
               System.out.println("str = " + str);
//
               Platform.runLater(() -> tfStartDiskMoney.setText(str)) ;
               System.out.println("[ Bluemarble ] 숫자만 입력할 수 있습니다.");
               break;
       }
    }
    @FXML void onClick2PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(5860000);
    }
    @FXML void onClick3PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(2930000);
    }
    @FXML void onClick4PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(2930000);
    }
    @FXML void onClickCustomDistMoneyButton(ActionEvent e) {
        tfStartDiskMoney.setDisable(false);
        tfStartDiskMoney.requestFocus();
    }
    @FXML void onClickDefaultDistMoneyButton(ActionEvent e) {
        if(rb2Player.isSelected()) setStartDistMoney(5860000);
        else setStartDistMoney(2930000);
        tfStartDiskMoney.setDisable(true);
    }
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
    void setStartDistMoney(long v){ tfStartDiskMoney.setText(df.format(v)); }
    void initStartBluemarbleModal() {
        setStartDistMoney(5860000);
        tfStartDiskMoney.setDisable(true);
        rbDefaultDistMoney.setSelected(true);
        rb2Player.setSelected(true);
        apStartBluemarbleModal.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStartBluemarbleModal();
    }
}
