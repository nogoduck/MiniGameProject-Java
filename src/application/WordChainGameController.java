package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WordChainGameController {

    @FXML private TextField inputWord;
    @FXML private Label lbMessage;
    @FXML private Label lbPrevWord;
    @FXML private Label lbScore;

    private Stage stage;
    private int score;
    private ArrayList<String> wordList = new ArrayList<String>();
    private char lastWord;
    private final int minLetterCnt = 2;

    private String DICT_KEY = "";

    String getKey()  {
        if(DICT_KEY != "") return DICT_KEY;
        File ap = new File(".");
        Path path = Paths.get(ap.getAbsolutePath() + "/src/application/config/key.txt");
        Charset cs = StandardCharsets.UTF_8;
        List<String> keyList = new ArrayList<String>();
        try {
            keyList = Files.readAllLines(path,cs);
        } catch(IOException e) { e.printStackTrace(); }
        for(String item : keyList){
            String[] itemArr = item.split(" : |: | :|:");
            if(itemArr[0].contains("DICT_KEY")) {
                DICT_KEY = itemArr[1];
                return itemArr[1];
            }
        }
        return "null";
    }

    void resWord(String word){
        try {
            URL url = new URL("https://stdict.korean.go.kr/api/view.do?key=" + getKey() + "&q" + word);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(">>" + line);

                //                if(line.contains("<word>"))
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isWord(String word){
        try {
            URL url = new URL("https://stdict.korean.go.kr/api/search.do?key=" + getKey() + "&q=" + word);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                if(line.contains("<word>")) return true;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    void setLastWord(String word){
        lastWord = word.charAt(word.length() - 1);
        System.out.println("lastWord = " + lastWord);
    }

    void setMessage(String message){
        lbMessage.setText(message);
    }

    void addScore(){
        lbScore.setText(Integer.toString(++score));
    }

    @FXML
    void onPressEnter(KeyEvent e) {
        if( e.getCode() == KeyCode.ENTER ) {
            String word = inputWord.getText();
            if(word.length() < minLetterCnt) {
                setMessage("단어는 최소 " + minLetterCnt + "글자 이상부터 입력할 수 있습니다.");
                return;
            }
            if(wordList.contains(word)) {
                setMessage("중복된 단어입니다.");
                return;
            }
            setMessage("");
            if(isWord(word)) {
                wordList.add(word);
                setLastWord(word);
                lbPrevWord.setText(word);
                inputWord.setText("");
                addScore();
                resWord(word);
            } else setMessage("등록되지 않은 단어입니다.");
        }
    }

    @FXML
    void onClickMainButton(ActionEvent e) throws IOException {
        Node node = (Node)(e.getSource());
        stage = (Stage)(node.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("미니게임");
        stage.setScene(scene);
        stage.show();
    }

}
