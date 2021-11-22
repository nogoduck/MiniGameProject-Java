package application.bluemarble;

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
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class BluemarbleGameController implements Initializable{
	final private byte goldCardNum = 10;	// 황금카드 갯수
	
	 @FXML void onClickRunDice(ActionEvent e) {
	  }
	 
	//   마우스 호버 액션
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
  
	
//  ==================================================
//               Start Bluemarble Modal
//  ==================================================
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


    @FXML
    void onType(KeyEvent e) {
        try{
            int position = tfStartDiskMoney.getCaretPosition();
            String str = tfStartDiskMoney.getText();
            String replaceStr = str.replaceAll("[^0-9]", "");
            Platform.runLater(() -> {
                        tfStartDiskMoney.setText(replaceStr);
                        tfStartDiskMoney.positionCaret(position);
                    }
            );
        } catch(Exception err) {
            System.out.println("[ Bluemarble ] 입력 에러 >> " + err.toString());
        }
    }


    @FXML void onClick2PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(586);
    }
    @FXML void onClick3PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(293);
    }
    @FXML void onClick4PlayerButton(ActionEvent e) {
        if(!rbDefaultDistMoney.isSelected()) return;
        setStartDistMoney(293);
    }
    @FXML void onClickCustomDistMoneyButton(ActionEvent e) {
        tfStartDiskMoney.setDisable(false);
        tfStartDiskMoney.requestFocus();
    }
    @FXML void onClickDefaultDistMoneyButton(ActionEvent e) {
        if(rb2Player.isSelected()) setStartDistMoney(586);
        else setStartDistMoney(293);
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
        setStartDistMoney(586);
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

	