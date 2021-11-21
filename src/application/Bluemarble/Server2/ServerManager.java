package application.Bluemarble.Server2;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {
    Socket socket;
    final String checkNicknameTag = "checkNickname";
    public static ArrayList userList = new ArrayList();
    public ServerManager(Socket socket) {
        this.socket = socket;
        receive();
    }

    public void receive() {
        Runnable thread = () -> {
            try{
                InputStream in = this.socket.getInputStream();
                DataInputStream din = new DataInputStream(in);

                while (true) {
                    String message = din.readUTF();
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
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("[ Receive ] Failed >> " + e.toString());
            }
        };
        Server.threadPool.submit(thread);
    }
        //클라이언트로부터 메시지를 전송하는 메소드
        public void send (String message){
            Runnable thread = () -> {
                try {
                    //메세지를 보내주고자 OutputStream 사용 (java.io로 import)
                    OutputStream out = socket.getOutputStream();
                    DataOutputStream dout = new DataOutputStream(out);

                    dout.writeUTF(message);
                    System.out.println("[ Send ] Succeed >> " + message);
                } catch (IOException e) {
                    System.out.println("[ Send ] Failed >> " + e.toString());
                }
            };
            Server.threadPool.submit(thread);
        }


    public void checkNickname(String payload[]){
        boolean flag = false;
        for(int i = 0; i < userList.size(); i++){
            if(payload[1].equals(userList.get(i))) flag = true;
        }
        if (!flag) {
            userList.add(payload[1]);
            System.out.println("[ Server ] 닉네임 중복 체크 >> 닉네임 사용가능");
        } else {
            System.out.println("[ Server ] 닉네임 중복 체크 >> 동일한 닉네임 존재");
        }
        send("@@payload:" + "##checkNickname" + "##" + flag + "##" + payload[1]);
    }

    void printPayload(String str[]) {
        System.out.printf("[ Receive ] Payload[] >> ");
        for (String s : str) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }


    void printAllUser(){
        System.out.println("[ Server ] userList[] >> ");
        for(int i = 0; i < userList.size(); i++){
            System.out.printf("%s ", userList.get(i));
        }
        System.out.printf("\n");
    }


}
