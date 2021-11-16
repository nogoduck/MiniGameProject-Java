package application.Bluemarble.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client {

    final String checkNicknameTag = "checkNickname";

    Socket socket;
    public static ArrayList userList = new ArrayList();
    public Client(Socket socket){
        this.socket = socket;
        receive();
    }

    void printPayload(String str[]){
        System.out.printf("payload >> ");
        for(String s:str){
            System.out.printf("%s ", s);
        }
        System.out.println();
    }

    //클라이언트로부터 메시지를 전달 받는 메소드
    public void receive(){
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try{
                    //클라이언트로부터 내용을 반복적으로 받기 위함
                    while(true){
                        //다른 컴퓨터로부터 어떠한 내용을 read함수를 사용하여 전달 받음
                        InputStream in = socket.getInputStream();
                        //한번에 512바이트씩 받는다.
                        byte[] buffer = new byte[512];
                        int length = in.read(buffer);
                        //메세지를 읽다가 오류 처리
                        while(length == -1) throw new IOException();
                        System.out.println("[메시지 수신 성공] " + socket.getRemoteSocketAddress() + ": "
                                + Thread.currentThread().getName());
                        //UTF-8로 한글도 처리가능하게 세팅
                        String message =  new String(buffer, 0, length, "UTF-8");
                        System.out.println("message >> " + message);
                        if(message.contains("@@payload:")){
                            String payload[] = message.replace("@@payload:##", "").split("##");
                            printPayload(payload);
                            //ex) payload[] >> [0]checkNickname, [1]"nickname"
                            System.out.println("checkNicknameTag >> " + checkNicknameTag);
                            System.out.println("contains >> " + payload[0].contains(checkNicknameTag));
                            System.out.println("equals >> " + payload[0].equals(checkNicknameTag));
                            System.out.println("payload[0], checkNicknameTag >> " + payload[0] + ", " + checkNicknameTag);
                            System.out.println("payload[0], payload[1], payload[2] >> " + payload[0] + ", " + payload[1] + ", ");
                            if(payload[0].contains(checkNicknameTag)) {
                                checkNickname(payload);
                            }
                        }

                        System.out.println("userList >> " + userList);

                        //1:n의 통신을 위해 다른 클라이언트에도 정보를 전송해 주는 반복문
//                        for(Client client: Main.clients){
//                            client.send(message);
//                        }
                    }
                } catch(Exception e){
                    //예외를 구체적으로 처리하기 위해 중첩해서 사용
                    try{
                        System.out.println("[메세지 수신 오류] "
                                + socket.getRemoteSocketAddress()
                                + ": " + Thread.currentThread().getName());
                        Main.clients.remove(Client.this);
                        socket.close();
                    } catch (Exception e2){
                        e2.printStackTrace();
                    }
                }
            }
        };

        //threadPool에 스레드를 등록해서 안정적으로 관리하게 해준다.
        Main.threadPool.submit(thread);
    }

    void checkNickname(String payload[]){
        if(userList.contains(payload[1])){
            System.out.println("동일한 닉네임이 존재합니다.");
            send("@@payload:" + "##checkNickname" + "##true" + "##" + payload[1]);
        } else {
            System.out.println("닉네임 사용가능");
            userList.add(payload[1]);
//            format 적용 예정
            send("@@payload:" + "##checkNickname" + "##false" + "##" + payload[1]);
        }
    }

    //클라이언트로부터 메시지를 전송하는 메소드
    public void send(String message){
        Runnable thread = new Runnable(){
            @Override
            public void run() {
                try{
                    //메세지를 보내주고자 OutputStream 사용 (java.io로 import)
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
                    //buffer에 담긴 내용을 서버에서 클라이언트로 전송
                    out.write(buffer);
                    //현재 위치까지 전송을 했다는 것을 알림
                    out.flush();
                } catch (Exception e){
                    try{
                        System.out.println("[메시지 송신 오류] "
                                + socket.getRemoteSocketAddress()
                                + ": " + Thread.currentThread().getName());
                        //오류가 발생하면 메인함수의 Client에 있는 배열(Vector로 사용함)에서
                        //해당 클라이언트를 제거해준다.
                        Main.clients.remove(Client.this);
                        //오류가 생긴 소켓을 종료한다.
                        socket.close();
                    } catch(Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }};
        Main.threadPool.submit(thread);
    }






}