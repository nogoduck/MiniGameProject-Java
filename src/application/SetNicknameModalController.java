package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SetNicknameModalController {

    private boolean enableNickname = false;
    @FXML private TextField ftNickname;
    @FXML private Label lbMessage;


    boolean isNickname(String nickname){
        //Server로 DB조회 요청 로직 작성


        return true;
    }


    void setEnableNickname(boolean b){ enableNickname = b; }
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

    @FXML
    void onClickCheckNickname(ActionEvent e) {
        final String NICKNAME = ftNickname.getText();
        if(NICKNAME.equals("")) {
            setMessage("닉네임을 입력해주세요.", false);
            return;
        }
        if(isNickname(NICKNAME)){
            setMessage("사용 가능한 닉네임입니다.", true);
            setEnableNickname(true);
            return;
        }
    }

    @FXML
    void onClickRunGame(ActionEvent e) {
        if(!enableNickname) return;


    }

}
