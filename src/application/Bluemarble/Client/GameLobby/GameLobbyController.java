package application.Bluemarble.Client.GameLobby;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import application.Bluemarble.Client.Client;
import application.Bluemarble.Client.GameRoom.GameRoomController;
import application.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//public class GameLobbyController extends Client implements Initializable{
public class GameLobbyController extends Client implements Initializable{

    @FXML public ScrollPane GameRoomContainer;
    @FXML public ScrollPane PlayerListContainer;
    @FXML public AnchorPane GameRoomCreateWindow;
    @FXML public AnchorPane connectFileWindows;
    @FXML public AnchorPane nicSetWindow;
    @FXML public TextField tfGameRoomCreateName;
    @FXML public TextField tfUserInputNickName;
    @FXML public AnchorPane serverConnectTryWindow;
    public boolean enableNickname = false;
    @FXML public Label lbMessage;
    @FXML public Text tUserNickname;
    public VBox roomBox = new VBox(5);
    private ArrayList<String> userList = new ArrayList<String>();

    public void setResMsg(String str[]){

        //ex) payload[] >> [0]checkNickname, [1]true OR false, [2]"nickname"
        if(str[0].contains("checkNickname")){
            if(str[1].contains("true")){
                setMessage("동일한 닉네임이 존재합니다.", false);
            } else {
                nicSetWindow.setVisible(false);
                tUserNickname.setText(str[2]);
            }
        } else if(str[0].contains("createRoom")){
            for(int i = 1; i < str.length - 1; i += 2){

                System.out.println("createRoom[] >> " + str[i]);
                roomBox.getChildren().add(new Label(str[i] + "   [인원] " + str[i + 1] + "/4"));
            }
        }
    }

    // 게임 로비 방만들기 버튼 클릭
    @FXML
    void onClickCreateRoom(ActionEvent event) {
    	GameRoomCreateWindow.setVisible(true);
    }
    
    // 방만들기 창 방만들기 버튼 클릭
    @FXML
    void onClickGameRoomCreateMake(ActionEvent e) throws IOException {
//        Client clent = new Client();
//        clent.send("@@payload:" + "##createRoom" + "##" + tfGameRoomCreateName.getText());

    }
    // 방만들기창 취소버튼 클릭
    @FXML
    void onClickGameRoomCreateCancle(ActionEvent event) {
    	GameRoomCreateWindow.setVisible(false);
    	tfGameRoomCreateName.clear();
    }
    // 게임 접속실패 칭에서 메인으로 돌아가는 버튼
    @FXML
    void onClickMain(ActionEvent e) throws IOException {
//    	client.stopClient();
    	Node node = (Node)(e.getSource());
        Stage stage = (Stage)(node.getScene().getWindow());
        Parent root = FXMLLoader.load(MainController.class.getResource("MainUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("부루마블");
        stage.setScene(scene);
        stage.show();
    }

    void setEnableNickname(boolean bool){ enableNickname = bool; }

    @FXML
    void onClickSetNicknameModal(ActionEvent e) {
    	nicSetWindow.setVisible(true);    			// 닉네임 설정창 활성화
    }

    void setMessage(String message, boolean type){
        //message: 메세지 / type: true(정상), false(불량)
        if (type){
            lbMessage.setText(message);
            lbMessage.setTextFill(Color.rgb(0, 148, 50));
        } else {
            lbMessage.setText(message);
            lbMessage.setTextFill(Color.rgb(238, 90, 36));
        }
    }
    // 닉네임 설정창에서 접속 버튼
    @FXML
    void onClickGameLobbyConnect(MouseEvent e) {
//        Client clent = new Client();



//        System.out.println("GameLobby Mouse Event >> " + e);
//        setMouseEvent(e);
        final String nickname = tfUserInputNickName.getText();
        userList.add(nickname);
        System.out.println("nickname >>  " + nickname);
        printUserList();
        send("@@payload:" + "##checkNickname" + "##" + nickname);

    }

    void printUserList(){
        System.out.printf("user >> ");
        for(int i = 0; i < userList.size(); i++){
            System.out.printf(userList.get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Client clent = new Client();
        GameRoomContainer.setContent(roomBox);
        startClient();
        nicSetWindow.setVisible(true);
    }
}
