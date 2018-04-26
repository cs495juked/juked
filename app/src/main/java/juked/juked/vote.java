package juked.juked;

public class vote {
    public String uri;
    public int vote;

    public vote(String u) {
        uri = u;
        vote = 0;
    }

    public void setVote(int v) {
        vote = v;
    }
    public int getVote() {return vote;}

    public void setUri(String u) {
        uri = u;
    }
    public String getURI() {
        return uri;
    }
}