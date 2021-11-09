package application.Bluemarble.Client;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class ClientTestController {
//
//    Socket socket = null;
//    BufferedReader in = null;
//    BufferedWriter out = null;
//
//    final private int PORT = 5005;
//
//       try {
//        socket = new Socket("localhost", PORT);
//
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//        while(true){
//            System.out.print("Send >> ");
//            String outMessage = "";
//
//            if(outMessage.equals("exit")){
//                out.write(outMessage + "\n");
//                out.flush();
//                System.out.println("Exit Server...");
//                break;
//            }
//            out.write(outMessage + "\n");
//            out.flush();
//            String inMessage = in.readLine();
//            System.out.println("Server >> " + inMessage);
//        }
//    } catch (IOException e){
//        e.printStackTrace();
//    } finally {
//        sc.close();
//        in.close();
//        out.close();
//        socket.close();
//    }

    @FXML
    private ScrollPane scrollContainer;

    @FXML
    private VBox vContainer;

}
