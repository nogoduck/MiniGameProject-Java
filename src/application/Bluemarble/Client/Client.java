package application.Bluemarble.Client;

import application.Bluemarble.Client.GameLobby.GameLobbyController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private MouseEvent me;
    public void setMouseEvent(MouseEvent e){
        me = e;
        node = (Node)me.getSource();
        stage = (Stage) node.getScene().getWindow();
    }

    Node node;
    Stage stage;
    Socket socket;
    OutputStream out;
    DataOutputStream dout;

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.socket = new Socket("localhost", 5005);

            System.out.println("[ Client ] Running...");

            client.out = client.socket.getOutputStream();
            client.dout = new DataOutputStream(client.out);

            System.out.println("client.out >> " + client.out);


            MessageListener msgListener = new MessageListener(client, client.socket);
            msgListener.start();

        } catch(SocketException e) {
            System.out.println("[ Client ] Client socket error >> " + e.toString());
        } catch(IOException e) {
            System.out.println("[ Client ] Input/Output error >> " + e.toString());
        }
    }


    public void send(String str) {
        try {
//            byte[] buffer = str.getBytes(StandardCharsets.UTF_8);
//            out.write(buffer);
//            dout.write(str.getBytes(StandardCharsets.UTF_8));


            System.out.println("[ Send ] Succeed >> " + str);
        } catch (Exception e) {
            System.out.println("[ Send ] Failed >> " + e.toString());
        }
    }
}

class MessageListener extends Thread{


    Socket socket;
    Client client;

    InputStream in;

    String message;	//수신 메시지 저장

    /* 각 메시지를 구분하기 위한 태그 */
    final String checkNicknameTag = "checkNickname";
    final String createRoomTag = "createRoom";
    final String enterRoomTag = "enterRoom";
    final String leaveRoomTag = "leaveRoom";

    MessageListener(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    public void run() {
        try {
            in = this.socket.getInputStream();

            while(true) {
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                while (length == -1) throw new IOException();
                message = new String(buffer, 0, length, "UTF-8");
                System.out.println("[ Receive ] Succeed >> " + message);

                if (message.contains("@@payload:")) {
                    String payload[] = message.replace("@@payload:##", "").split("##");
                    printPayload(payload);

                    switch (payload[0]) {
                        case checkNicknameTag:
                            System.out.println("[ Server ] 닉네임 중복 체크");
                            //payload[] >> [0]checkNickname, [1]nickname
                            checkNickname(payload);
                            break;
                        case createRoomTag:
                            System.out.println("[ Server ] 방 생성");
                            //payload[] >> [0]createRoom, [1]roomTitle
//                            createRoom(payload);
                            break;
                        case enterRoomTag:
                            System.out.println("[ Server ] 방 입장");
                            //payload[] >> [0]enterRoom, [1]roomTitle
//                            enterRoom(payload);
                            break;
                        case leaveRoomTag:
                            System.out.println("[ Server ] 방 퇴장");
                            //payload[] >> [0]leaveRoom, [1]roomTitle
//                            leaveRoom(payload);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[ Receive ] Failed >> " + e.toString());
        }
    }



    void checkNickname(String payload[]){
        System.out.println("닉네임 확인 ");
//        Platform.runLater(() -> {
//            Stage stage = new Stage();
//            FXMLLoader loader = new FXMLLoader(GameLobbyController.class.getResource("GameLobbyUI.fxml"));
//            Parent root = null;
//            try {
//                root = (Parent)loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            GameLobbyController controller = loader.<GameLobbyController>getController();
//            controller.setResMsg(payload);
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        });
    }

    void printPayload(String str[]) {
        System.out.printf("[ Receive ] Payload[] >> ");
        for (String s : str) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }


}