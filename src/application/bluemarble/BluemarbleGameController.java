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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    @FXML private ToggleGroup startDistMoneyGroup;
    @FXML private ToggleGroup PlayerCntGroup;
    @FXML private RadioButton rb2Player;
    @FXML private RadioButton rb3Player;
    @FXML private RadioButton rb4Player;
    @FXML private RadioButton rbDefaultDistMoney;
    @FXML private RadioButton rbCustomDistMoney;
    @FXML private TextField tfStartDistMoney;
    @FXML private TextField tf1PlayerNickname;
    @FXML private TextField tf2PlayerNickname;
    @FXML private TextField tf3PlayerNickname;
    @FXML private TextField tf4PlayerNickname;
    @FXML private Pane pPlayer1;
    @FXML private Pane pPlayer2;
    @FXML private Pane pPlayer3;
    @FXML private Pane pPlayer4;
    private boolean[] selectPlayer = new boolean[5];
    private int selectCharacterCnt;
    private int selectedCharacterCnt;



    void onToggleCharacterCard(MouseEvent e, int i){
        Node source = (Node)e.getSource();
        String cardId = source.idProperty().getValue();
        System.out.println("toggleCard >> " + cardId.charAt(cardId.length() - 1));


        if(selectedCharacterCnt < selectCharacterCnt) System.out.println("초과");

        if(!selectPlayer[i]){
            //1번 플레이어 선택
            System.out.println("1번 선택");
            source.setStyle("-fx-opacity: 0;-fx-background-color: #000000");
            selectedCharacterCnt++;
            selectPlayer[i] = true;
        } else {
            //1번 플레이어 선택 해제
            System.out.println("1번 선택 해제");
            source.setStyle("-fx-opacity: 0.5;-fx-background-color: #000000");
            selectedCharacterCnt--;
            selectPlayer[i] = false;
        }
        System.out.println("selectCharacterCnt >> " + selectCharacterCnt);
        System.out.println("selectedCharacterCnt >> " + selectedCharacterCnt);
    }


    @FXML void onClickCharacter1(MouseEvent e) {
        onToggleCharacterCard(e, 1);
    }

    @FXML void onClickCharacter2(MouseEvent e) {
        onToggleCharacterCard(e, 2);

    }

    @FXML void onClickCharacter3(MouseEvent e) {
        onToggleCharacterCard(e, 3);
    }

    @FXML void onClickCharacter4(MouseEvent e) {
        onToggleCharacterCard(e, 4);

    }


    @FXML void onType(KeyEvent e) {
        try{
            int position = tfStartDistMoney.getCaretPosition();
            String str = tfStartDistMoney.getText();
            String replaceStr = str.replaceAll("[^0-9]", "");
            Platform.runLater(() -> {
                        tfStartDistMoney.setText(replaceStr);
                        tfStartDistMoney.positionCaret(position);
                    }
            );
        } catch(Exception err) {
            System.out.println("[ Bluemarble ] 입력 에러 >> " + err.toString());
        }
    }

    @FXML void onClick2PlayerButton(ActionEvent e) {
        if(rbDefaultDistMoney.isSelected()) setStartDistMoney(586);
        selectCharacterCnt = 2;
    }
    @FXML void onClick3PlayerButton(ActionEvent e) {
        if(rbDefaultDistMoney.isSelected()) setStartDistMoney(293);
        selectCharacterCnt = 3;
    }
    @FXML void onClick4PlayerButton(ActionEvent e) {
        if(rbDefaultDistMoney.isSelected()) setStartDistMoney(293);
        selectCharacterCnt = 4;
    }
    @FXML void onClickCustomDistMoneyButton(ActionEvent e) {
        tfStartDistMoney.setDisable(false);
        tfStartDistMoney.requestFocus();
    }
    @FXML void onClickDefaultDistMoneyButton(ActionEvent e) {
        if(rb2Player.isSelected()) setStartDistMoney(586);
        else setStartDistMoney(293);
        tfStartDistMoney.setDisable(true);
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
    @FXML	// 마우스 호버 이벤트
    void onHoverEnterCard(MouseEvent e) {
        Node source = (Node)e.getSource();
        switch (source.idProperty().getValue()){
            case "pPlayer1":
                if (selectPlayer[1]) return;
            case "pPlayer2":
                if (selectPlayer[2]) return;
            case "pPlayer3":
                if (selectPlayer[3]) return;
            case "pPlayer4":
                if (selectPlayer[4]) return;
            default:
                source.setStyle("-fx-cursor:hand;-fx-background-color: #000000;-fx-opacity: 0.1");
        }
    }
    @FXML	// 마우스 호버 끝난경우
    void onHoverExitCard(MouseEvent e) {
        Node source = (Node)e.getSource();
        switch (source.idProperty().getValue()){
            case "pPlayer1":
                if (selectPlayer[1]) return;
            case "pPlayer2":
                if (selectPlayer[2]) return;
            case "pPlayer3":
                if (selectPlayer[3]) return;
            case "pPlayer4":
                if (selectPlayer[4]) return;
            default:
                source.setStyle("-fx-cursor:hand;-fx-background-color: #000000;-fx-opacity: 0.5");
        }
    }

    void setStartDistMoney(long v){ tfStartDistMoney.setText(df.format(v)); }
    void initStartBluemarbleModal() {
        setStartDistMoney(586);
        tfStartDistMoney.setDisable(true);
        rbDefaultDistMoney.setSelected(true);
        rb2Player.setSelected(true);
        apStartBluemarbleModal.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStartBluemarbleModal();
    }
}

	