package juked.juked;

import java.util.ArrayList;

public class jukeuser {
    public int userId=0;
    public String userName = "Default User ";
    public Song song = null;
    public int host = 0;

    //user constructor
    public jukeuser (int a, String s, int h){
       userId = a;
       userName = s;
       song = null;
       host = h;
    }
    public jukeuser () {

    }

    public void setUserSong (Song newSong) {
        song = newSong;
    }
}
