package application.Bluemarble.Client;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;

public class Client {
    private MouseEvent me;
    public void setMouseEvent(MouseEvent e){
        me = e;
        node = (Node)me.getSource();
        stage = (Stage) node.getScene().getWindow();
    }

    Node node;
    Stage stage;
    Socket mySocket;
    OutputStream out = null;
    DataOutputStream dout = null;

//    public static void main(String[] args) {
    public void startClient(){
        try {
//            client.socket = new Socket("localhost", 5005);
            mySocket = new Socket("localhost", 5005);




            out = mySocket.getOutputStream();
            dout = new DataOutputStream(out);
            System.out.println("[ Client ] Running...");

//            send("Good Game, GG");

            System.out.println("client.out >> " + out);
            System.out.println("client.dout >> " + dout);


//            ClientManager clientManager = new ClientManager(client, client.mySocket);
            ClientManager clientManager = new ClientManager(mySocket);
            clientManager.start();

        } catch(SocketException e) {
            System.out.println("[ Client ] Client socket error >> " + e.toString());
        } catch(IOException e) {
            System.out.println("[ Client ] Input/Output error >> " + e.toString());
        }
    }


    public void send(String str) {
        System.out.println("socket >> " + mySocket.getInetAddress());
        try {
//            byte[] buffer = str.getBytes(StandardCharsets.UTF_8);
//            out.write(buffer);
//            dout.write(str.getBytes(StandardCharsets.UTF_8));

            dout.writeUTF(str);
            System.out.println("[ Send ] Succeed >> " + str);
        } catch (Exception e) {
            System.out.println("[ Send ] Failed >> " + e.toString());
        }
    }
}
