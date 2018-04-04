package juked.juked;

public class jukeuser {
    public int userId=0;
    public String userName = "Default User ";
    public String song = "Default song";
    public int host = 0;

    //user constructor
    public jukeuser (int a, String s, String t, int h){
       userId = a;
       userName = s;
       song = t;
       host = h;
    }
}
