package juked.juked;

public class Vote {
    private String uri;
    private int vote;
    private String userID;

    public Vote(String u, String uID) {
        uri = u;
        vote = 0;
        userID = uID;
    }
    public Vote() {

    }

    public void setUID(String uid) {
        userID = uid;
    }

    public String getUID() { return userID; }

    public void setVote(int v) {
        vote = v;
    }
    public int getVote() { return vote; }

    public void setURI(String u) {
        uri = u;
    }
    public String getURI() { return uri; }
}
