package application.Bluemarble.Client;

import application.Bluemarble.Client.GameRoom.GameRoomController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main extends Application {

    public static String reqMessage;

    Socket socket;
    TextArea textArea;

    //클라이언트 프로그램 동작 메서드
    public void startClient(String IP, int PORT){
        Thread thread = new Thread(){
            public void run(){
                try {
                    socket = new Socket(IP, PORT);
                    //서버로부터 메시지를 전달받기 위한 메서드
                    receive();
                } catch(Exception e) {
                    if(!socket.isClosed()){
                        stopClient();
                        System.out.println("[서버 접속 실패]");
                        //프로그램 종료
                        Platform.exit();
                    }
                }
            }};
        thread.start();
    }

    //클라이언트 프로그램 종료 메서드
    public void stopClient(){
        try {
            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //서버로부터 메시지를 전달받는 메서드
    public void receive(){
        while(true){
            try {
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                if(length == -1) throw new IOException();
                String message = new String(buffer, 0, length, "UTF-8");
                Platform.runLater(() -> {
                    System.out.println("receive() Message >> " + message);

//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(GameRoomController.class.getResource("GameLobby.fxml"));
//                    try {
//                        Parent root = loader.load();
//                        loader.getClass().setData(message);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    //                    reqMessage = message;


                    if(message.contains("##nickname")){
                        String str = message.replace("##nickname", "");
//                        GameLobbyController.push(str);
//                        GameLobbyController glc = new GameLobbyController(str);
                    }

                    //                    textArea.appendText(message);
                });
            } catch(Exception e) {
                stopClient();
                break;
            }
        }
    }

    //서버로 메시지를 전송하는 메서드
    public void send(String message){
        Thread thread = new Thread(() -> {
            try {
                OutputStream out = socket.getOutputStream();
                //보내고자 하는 정보를 UTF-8로 인코딩해서 보내준다
                //서버도 UTF-8로 받을 수 있게 되있음
                byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
                System.out.println("message >> " + message);
                out.write(buffer);
                //메시지 전송의 끝을 알림
                out.flush();
            } catch(Exception e){
                stopClient();
            }
        });
        thread.start();
    }


    //클라이언트 프로그램을 동작시키는 메서드
    @Override
    public void start(Stage primaryStage) throws IOException {
//        startClient("localhost", 5005);
    }
    public static void main(String[] args) { launch(args); }
}