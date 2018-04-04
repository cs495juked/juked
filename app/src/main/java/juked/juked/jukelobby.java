package juked.juked;

import java.util.ArrayList;

public class jukelobby {
    public int lobbyId = 0000;

    //need to make arraylist specific to user collection later
    public ArrayList<jukeuser> userList = new ArrayList();

    public jukelobby (int id, jukeuser user) {
        lobbyId = id;
        userList.add(user);
    }
}
