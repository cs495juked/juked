package juked.juked;
import java.util.*;

public class PlaylistSong {
    private String songName;
    private String artistName;
    private String albumName;
    private String albumArtwork;
    private String vote;
    private String songURI;
    private String adderNickname;
    private int voteTotal;

    public PlaylistSong(){

    }



    public PlaylistSong(String songName, String artistName, String albumName, String albumArtwork, String songURI, String adderNickname, int voteTotal) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.albumArtwork = albumArtwork;
        this.adderNickname = adderNickname;
        this.vote = "down";
        this.songURI = songURI;
        this.voteTotal= voteTotal;

    }

    public String getSongURI() { return songURI; }

    public void setSongURI(String songURI) { this.songURI = songURI; }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String aristName) {
        this.artistName = aristName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtwork() { return albumArtwork; }

    public void setAlbumArtwork(String albumArtwork) { this.albumArtwork = albumArtwork; }

    public String getVote() { return vote; }

    public void setVote(String vote) { this.vote = vote; }

    public String getAdderNickname() { return adderNickname; }

    public void setAdderNickname(String adderNickname) { this.adderNickname = adderNickname; }

    public int getVoteTotal() { return voteTotal; }

    public void setVoteTotal(int voteTotal) { this.voteTotal = voteTotal; }


}


 class SortPlaylist implements Comparator<PlaylistSong>{
    public int compare(PlaylistSong a, PlaylistSong b){
        return b.getVoteTotal()-a.getVoteTotal();
    }
}
