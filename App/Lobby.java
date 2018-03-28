/*
    Authors: Francisco R.
    About:
        This file is for when a user hits the "Join Lobby"
        button. They will enter a lobby code that was given to them
        to be tested against the server. It should take the users deviceID and
        prompt them for a username that will be associated with each other as well
        as some other information to be sent to the database.
 */

public class Lobby {
    private int lobbyCode;
    private int lobbySize;

    pub

    int createLobby(str deviceID, str userID);
    void joinLobby(int lobbyCode, str deviceID, str userID);
    void destroyLobby();
    void leaveLobby(ind deviceID);
    Playlist createPlaylist();
    User createUser(str deviceID, str userID);
    int getLobbyID();
    int getLobbySize();
    void addUser();
    int lobbySize;
    int lobbyCode;
    int Users [];




}

