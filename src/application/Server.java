package application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public static void main(String[] args) throws IOException {

        // 참고용 (사용안함)
        // 적용 예정 경로: Bluemable.Server.Server.java
        ServerSocket server = null;
        Socket socket = null;
        BufferedReader in = null; //입력
        BufferedWriter out = null; //출력

        Scanner sc = new Scanner(System.in);

        final int PORT = 5005;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Client Connect...");
            socket = server.accept();
            System.out.println("Client is Connected");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true){
                String inMessage = in.readLine();
                if(inMessage.equals("exit")){
                    System.out.println("Exit Client...");
                    break;
                }

                System.out.println("Client >> " + inMessage);
                System.out.print("Send >> ");
                String outMessage = sc.nextLine();
                out.write(outMessage + "\n");
                out.flush();


            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            sc.close();
            in.close();
            out.close();
            socket.close();
            server.close();


        }

    }
}
