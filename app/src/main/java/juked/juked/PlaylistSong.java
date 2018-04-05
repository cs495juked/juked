package juked.juked;

public class PlaylistSong {
    private String songName;
    private String artistName;
    private String albumName;

    public PlaylistSong(){

    }


    public PlaylistSong(String songName, String aristName, String albumName) {
        this.songName = songName;
        this.artistName = aristName;
        this.albumName = albumName;
    }

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
}
