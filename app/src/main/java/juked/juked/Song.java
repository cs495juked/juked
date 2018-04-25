package juked.juked;

/**
 * Written by jgmills
 */
public class Song {


    private int songLength;

    //TODO: change type to USER once the object has been created
    private String songAdder;

    private String songURI;

    private int voteBalance;
    private String songName;
    private String artistName;
    private String albumCover;
    private String albumName;
    private int position;

    //TODO: add length and everything back to constructor
    //changing for testing purposes
    public Song(int length, String uri, String sName, String aName, String cover, String ALname){

        songLength = length;
        songAdder = "me";
        songURI = uri;
        songName = sName;
        artistName = aName;
        voteBalance = 0;
        albumCover = cover;
        albumName = ALname;
        position = 0 ;

    }
    public Song() {

    }
    //end song constructor


    public int getSongLength(){
        return songLength;
    }

    //TODO: add getters and setters for songAdder

    public String getSongURI(){
        return songURI;
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

    public String getAlbumCover() { return albumCover; }

    public int getPosition() {return position; }

    public void setPosition(int pos) {position = pos;}

}//end class song