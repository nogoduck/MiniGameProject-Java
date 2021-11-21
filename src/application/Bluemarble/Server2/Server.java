package application.Bluemarble.Server2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends Application {


    public static ExecutorService threadPool;
    public static Vector<ServerManager> clients = new Vector<ServerManager>();
    ServerSocket serverSocket;

    public void startServer(String IP, int PORT){
        try{
            serverSocket = new ServerSocket();

            serverSocket.bind(new InetSocketAddress(IP, PORT));
        } catch (Exception e) {
            e.printStackTrace();
            if(!serverSocket.isClosed()){
                stopServer();
            }
            return;
        }

        Runnable thread = () -> {
            while(true){
                try {
                    Socket socket = serverSocket.accept();
                    clients.add(new ServerManager(socket));
                    System.out.println("socket >> " + socket);
                    System.out.println("[클라이언트 접속] "
                            + socket.getRemoteSocketAddress()
                            +": " + Thread.currentThread().getName());
                    System.out.println("-------------");
                } catch (IOException e) {
                    if(!serverSocket.isClosed()){
                        stopServer();
                    }
                    break;
                }
            }
        };
        threadPool = Executors.newCachedThreadPool();
        threadPool.submit(thread);
    }


    public void stopServer(){
        try{

            Iterator<ServerManager> iterator = clients.iterator();
            while(iterator.hasNext()){
                ServerManager client = iterator.next();
                client.socket.close();
                iterator.remove();
            }
            if(serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
            }
            if(threadPool != null && !threadPool.isShutdown()){
                threadPool.shutdown();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // UI를 생성하고, 프로그램을 동작시키는 메소드
    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();
        //내부에 5만큼의 패딩 적용
        root.setPadding(new Insets(5));
        TextArea textArea = new TextArea();
        //textarea를 출력만하고 사용자가 수정하지 못하게 하는 속성
        textArea.setEditable(false);
        textArea.setFont(new Font("CookieRun", 15));
        //중간에 textArea를 담는다.
        root.setCenter(textArea);

        Button toggleButton = new Button("시작하기");
        toggleButton.setMaxWidth(Double.MAX_VALUE);
        BorderPane.setMargin(toggleButton, new Insets(1, 0, 0, 0));
        root.setBottom(toggleButton);

        final String IP = "127.0.0.1";
        final int PORT = 5005;

        toggleButton.setOnAction(e -> {
            if(toggleButton.getText().equals("시작하기")){
                startServer(IP, PORT);
                //JavaFX는 runLater 함수를 사용하여 GUI 요소를 변경해줘야한다.
                Platform.runLater(() -> {
                    String message = String.format("[서버 시작]\n", IP, PORT);
                    textArea.appendText(message);
                    toggleButton.setText("종료하기");
                });
            } else {
                stopServer();
                Platform.runLater(() -> {
                    String message = String.format("[서버 종료]\n", IP, PORT);
                    textArea.appendText(message);
                    toggleButton.setText("시작하기");
                });
            }
        });

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("[채팅 서버]");
        primaryStage.setOnCloseRequest(e -> stopServer());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}