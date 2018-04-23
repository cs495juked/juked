package juked.juked;

public class PlaylistSong {
    private String songName;
    private String artistName;
    private String albumName;
    private String albumArtwork;
    private String vote = "none";
    private String songURI;

    public PlaylistSong(){

    }


    public PlaylistSong(String songName, String artistName, String albumName, String albumArtwork, String songURI) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.albumArtwork = albumArtwork;
        this.vote = "none";
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

}
