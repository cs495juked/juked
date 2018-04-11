package juked.juked;

/**
 * Written by jgmills
 */
public class Song {


    private int songLength;

    //TODO: change type to USER once the object has been created
    private String songAdder;

    private String songURI;

    private int upVotes;
    private int downVotes;
    private int numVotes;
    private int voteBalance;
    private String songName;
    private String artistName;
    private String albumCover;
    private String albumName;

    //TODO: add length and everything back to constructor
    //changing for testing purposes
    public Song(int length, String uri, String sName, String aName, String cover, String ALname){

        songLength = length;
        songAdder = "me";
        songURI = uri;
        songName = sName;
        artistName = aName;
        upVotes = 0;
        downVotes = 0;
        numVotes = 0;
        voteBalance = 0;
        albumCover = cover;
        albumName = ALname;

    }//end song constructor


    public int getSongLength(){
        return songLength;
    }

    //TODO: add getters and setters for songAdder

    public String getSongURI(){
        return songURI;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int vote){
        upVotes = vote;
    }

    public int getDownVotes(){
        return downVotes;
    }

    public void setDownVotes(int vote){
        downVotes = vote;
    }

    public int getNumVotes(){
        return numVotes;
    }

    public void setNumVotes(int vote){
        numVotes = vote;
    }

    public int getVoteBalance(){
        return voteBalance;
    }

    public void setVoteBalance(int vote){
        voteBalance = vote;
    }

    public String getAlbumName(){
        return albumName;
    }

    public String getSongName(){
        return songName;
    }

    public String getArtistName(){
        return artistName;
    }

}//end class song