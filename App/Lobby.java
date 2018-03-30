/*
    Authors: Francisco R.
    About:
        This file is for when a user hits the "Join Lobby"
        button. They will enter a lobby code that was given to them
        to be tested against the server. It should take the users deviceID and
        prompt them for a username that will be associated with each other as well
        as some other information to be sent to the database.
 */

// need to include userClass?

public class Lobby {
    private int lobbyCode;
    private int lobbySize;
    int[] users;

    // getters & setters
    public int getLobbyCode() {
        // does this need to come from the DB?
        // return DBlobbyCode
        return lobbyCode;
    }

    public void setLobbyCode(int lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public int getLobbySize() {
        return lobbySize;
    }

    public void incrementLobbySize(int lobbySize) {
        this.lobbySize = lobbySize + 1;
    }
    // end getters & setters

    pubic int createLobby(int deviceID, str userID) {



        return getLobbyCode();
    }
    void joinLobby(int lobbyCode, str deviceID, str userID) {

    }
    void destroyLobby() {

    }

    void leaveLobby(int deviceID) {

    }


    /* Playlist createPlaylist() {

    } */

    void addUser() {

    }

}

