package juked.juked;

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

    public void userSong (Song newSong) {
        song = newSong;
    }
}
