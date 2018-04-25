package juked.juked;

import java.util.ArrayList;
import java.util.Comparator;

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
class SortUsers implements Comparator<jukeuser> {
    public int compare (jukeuser a, jukeuser b) {
        if (a.song == null) {
            return 1;
        }
        if (b.song == null) {
            return -1;
        }
        if ((a.song != null) && (b.song != null)) {
            return b.song.getVoteBalance()-a.song.getVoteBalance();
        }
        else {
            return 0;
        }
    }
}
