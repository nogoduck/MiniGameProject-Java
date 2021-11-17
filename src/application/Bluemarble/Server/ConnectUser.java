package application.Bluemarble.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Vector;

class ConnectUser extends Thread {
    Server server;
    Socket socket;

    /* 각 객체를 Vector로 관리 */
    Vector<ConnectUser> allUser;    //연결된 모든 클라이언트
    Vector<ConnectUser> waitUser;    //대기실에 있는 클라이언트
    Vector<Room> room;        //생성된 Room
    ArrayList userList = new ArrayList();

    /* 메시지 송수신을 위한 필드 */
    InputStream in;
    OutputStream out;

    String message;            //수신 메시지를 저장할 필드
    String nickname;    //클라이언트의 닉네임을 저장할 필드
    Room myRoom;        //입장한 방 객체를 저장할 필드

    /* 각 메시지를 구분하기 위한 태그 */
    final String checkNicknameTag = "checkNickname";
    final String createRoomTag = "createRoom";
    final String EnterRoomTag = "enterRoom";
    final String roomListTag = "roomList";

    ConnectUser(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        allUser = server.userAllList;
        waitUser = server.userWaitList;
        room = server.roomList;
    }

    public void run() {
        try {
            System.out.println("[ Server ] Client is Connected >> " + this.socket.toString());
            System.out.println("[ Server ] Client, Thread info >> " + socket.getRemoteSocketAddress() + ", "
                    + Thread.currentThread().getName());

            in = this.socket.getInputStream();
            out = this.socket.getOutputStream();

            System.out.println("this >> " + this);
            System.out.println("allUser >> " + allUser);



            while (true) {
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
//                            payload[] >> [0]checkNickname, [1]nickname
                            checkNickname(payload);
                            break;
                        case createRoomTag:
//                            payload[] >> [0]createRoom, [1]roomTitle
                            createRoom(payload);
                            break;

                    }

                }

                System.out.println("userList >> " + userList);
            }
        } catch (IOException e) {
            System.out.println("[ Receive ] Failed >> " + e.toString());
        }
    }


    void sendWaitRoom(String str){
        for(int i = 0; i < waitUser.size(); i++){
            try{

            } catch(Exception e){

            }
        }
    }


    String checkRoomList(){
        String str = "";
        for(int i = 0; i < room.size(); i ++) {
            str += room.get(i).title + "##" + room.get(i).userCnt;
        }
        return str;
    }

    void createRoom(String payload[]){
        myRoom = new Room();
        myRoom.userCnt++;
        myRoom.title = payload[1];
        room.add(myRoom);
        myRoom.connectUsers.add(this);
        waitUser.remove(this);
    }

    void checkNickname(String payload[]) {
        if (true) {
            allUser.add(this);
            waitUser.add(this);




            System.out.println("닉네임 사용가능");
            userList.add(payload[1]);
            send("@@payload:" + "##checkNickname" + "##false" + "##" + payload[1]);
        } else {
            System.out.println("동일한 닉네임이 존재합니다.");
            send("@@payload:" + "##checkNickname" + "##true" + "##" + payload[1]);
        }
    }

    void printPayload(String str[]) {
        System.out.printf("[ Receive ] Payload[] >> ");
        for (String s : str) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }

    void send(String str) {
        try {
            System.out.println("[ Send ] Succeed >> " + str);
            byte[] buffer = str.getBytes(StandardCharsets.UTF_8);
            out.write(buffer);
        } catch (Exception e) {
            System.out.println("[ Send ] Failed >> " + e.toString());
        }
    }

}
