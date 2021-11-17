package application.Bluemarble.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Vector;


public class Server {
    ServerSocket serverSocket = null;

    Vector<ConnectUser> userAllList;
    Vector<ConnectUser> userWaitList;
    Vector<Room> roomList;

    public static void main(String[] args) {
        Server server = new Server();

        server.userAllList = new Vector<>();
        server.userWaitList = new Vector<>();
        server.roomList = new Vector<>();

        try {
            //서버 소켓 준비
            server.serverSocket = new ServerSocket(5005);
            System.out.println("[ Server ] Running...");

            //클라이언트의 연결 요청을 상시 대기.
            while(true) {
                Socket socket = server.serverSocket.accept();
                ConnectUser connectUser = new ConnectUser(socket, server);	//소켓과 서버를 넘겨 ConnectUser(접속한 유저 관리)객체 생성

                connectUser.start();	//ConnectUser 스레드 시작
            }
        } catch(SocketException e) {	//각 오류를 콘솔로 알린다.
            System.out.println("[ Server ] Server socket error >> " + e.toString());
        } catch(IOException e) {
            System.out.println("[ Server ] Input/Output error >> " + e.toString());
        }
    }
}

