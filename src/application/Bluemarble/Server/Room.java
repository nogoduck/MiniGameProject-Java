package application.Bluemarble.Server;

import java.util.Vector;

public class Room {
    Vector<ServerManager> connectUsers;
    String title;
    int userCnt;
    Room(){
        connectUsers = new Vector<>();
    }
}
